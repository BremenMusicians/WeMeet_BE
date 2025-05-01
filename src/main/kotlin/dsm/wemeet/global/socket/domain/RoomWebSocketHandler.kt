package dsm.wemeet.global.socket.domain

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.wemeet.domain.room.exception.AlreadyJoinedRoomException
import dsm.wemeet.domain.room.usecase.CheckIsMemberUseCase
import dsm.wemeet.domain.room.usecase.KickMemberUseCase
import dsm.wemeet.domain.room.usecase.LeaveRoomUseCase
import dsm.wemeet.domain.room.usecase.UpdateMemberPositionUseCase
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.global.error.exception.BadRequestException
import dsm.wemeet.global.socket.vo.Peer
import dsm.wemeet.global.socket.vo.Signal
import org.json.JSONObject
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.Optional
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.CopyOnWriteArrayList

@Component
class RoomWebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val kickMemberUseCase: KickMemberUseCase,
    private val leaveRoomUseCase: LeaveRoomUseCase,
    private val checkIsMemberUseCase: CheckIsMemberUseCase,
    private val updateMemberPositionUseCase: UpdateMemberPositionUseCase
) : TextWebSocketHandler() {

    private val roomPeers: ConcurrentMap<UUID, CopyOnWriteArrayList<WebSocketSession>> = ConcurrentHashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val roomId = getRoomId(session)
        val peers = roomPeers.computeIfAbsent(roomId) { CopyOnWriteArrayList() }

        try {
            // 멤버가 이 방에 포함되고 있는지 확인
            checkIsMemberUseCase.execute(roomId, getUserEmail(session))

            // 이미 세션에 들어와있는지
            peers.find { getUserEmail(it) == getUserEmail(session) }?.let { throw AlreadyJoinedRoomException }
        } catch (e: Exception) {
            session.close(CloseStatus.POLICY_VIOLATION)
        }

        // 기존 멤버들에게 새로 참가하는 멤버 정보 전송
        val joinMsg = createMsg("join", objectMapper.writeValueAsString(session.toPeer()))
        peers.forEach { peer ->
            if (peer.isOpen) peer.sendMessage(TextMessage(joinMsg.toString()))
        }

        // 신규 피어에게 기존 멤버 정보 발송
        val existsPeerMsg = createMsg("exist", objectMapper.writeValueAsString(peers.map { it.toPeer() }))
        if (session.isOpen) session.sendMessage(TextMessage(existsPeerMsg.toString()))

        peers += session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) = leaveAndCleanUp(session)

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        // 통신 오류가 발생했으므로 도메인 상태를 먼저 정리
        runCatching {
            leaveRoomUseCase.execute(
                roomId = getRoomId(session),
                currentUserEmail = getUserEmail(session)
            )
        }

        leaveAndCleanUp(session)

        // 기본 처리(세션 close 등) 수행
        super.handleTransportError(session, exception)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val roomId = getRoomId(session)
        val peers = roomPeers[roomId] ?: return

        val signal = objectMapper.readValue(message.payload, Signal::class.java)

        when (signal.type) {
            // WebRTC 연결 정보 관련 타입
            "offer", "answer", "candidate" -> {
                signal.to?.let { targetEmail ->
                    peers.find { it.attributes["email"] == targetEmail }
                        ?.takeIf { it.isOpen }
                        ?.sendMessage(TextMessage(objectMapper.writeValueAsString(signal)))
                }
            }

            "kick" -> {
                val currentEmail = getUserEmail(session)

                try {
                    kickMemberUseCase.execute(roomId, currentEmail, signal.to!!)
                } catch (e: Exception) {
                    return
                }

                peers.find { it.attributes["email"] == signal.to }
                    ?.takeIf { it.isOpen }
                    ?.close(CloseStatus(4003))
            }

            "position" -> {
                signal.data?.let {
                    val position = Position.valueOf(it.get("position").asText())
                    val mail = getUserEmail(session)

                    updateMemberPositionUseCase.execute(roomId, mail, position)

                    val positionMsg = Signal(
                        type = signal.type,
                        to = null,
                        data = objectMapper.createObjectNode().apply {
                            put("mail", mail)
                            put("position", position.name)
                        }
                    )

                    peers.forEach { peer ->
                        if (peer.isOpen) peer.sendMessage(TextMessage(objectMapper.writeValueAsString(positionMsg.toString())))
                    }
                }
            }
        }
    }

    private fun leaveAndCleanUp(session: WebSocketSession) {
        val roomId = getRoomId(session)

        roomPeers[roomId]?.let { list ->
            list.remove(session)

            // 남은 멤버에게 퇴장 발송
            val leaveMsg = createMsg("leave", objectMapper.writeValueAsString(session.toPeer()))
            list.forEach { peer ->
                if (peer.isOpen) peer.sendMessage(TextMessage(leaveMsg.toString()))
            }

            if (list.isEmpty()) roomPeers.remove(roomId)
        }
    }

    private fun getRoomId(session: WebSocketSession): UUID =
        runCatching {
            UUID.fromString(session.attributes["roomId"]!!.toString())
        }.getOrElse {
            throw BadRequestException
        }

    // 이미 AuthorizerInterceptor 에서 타입 확인을 해줬음
    private fun getUserEmail(session: WebSocketSession): String =
        session.attributes["email"]!!.toString()

    private fun getAccountId(session: WebSocketSession): String =
        session.attributes["accountId"]!!.toString()

    private fun getProfile(session: WebSocketSession): String? =
        (session.attributes["profile"]!! as Optional<String>).orElse(null)

    private fun createMsg(type: String, payload: String): JSONObject =
        JSONObject()
            .put("type", type)
            .put("payload", payload)

    private fun WebSocketSession.toPeer() = Peer(
        getUserEmail(this),
        getAccountId(this),
        getProfile(this)
    )
}
