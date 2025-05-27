package dsm.wemeet.global.socket.domain

import WeMeetException
import com.fasterxml.jackson.databind.ObjectMapper
import dsm.wemeet.domain.room.exception.AlreadyJoinedRoomException
import dsm.wemeet.domain.room.exception.RoomNotFoundException
import dsm.wemeet.domain.room.usecase.CheckIsMemberUseCase
import dsm.wemeet.domain.room.usecase.KickMemberUseCase
import dsm.wemeet.domain.room.usecase.LeaveRoomUseCase
import dsm.wemeet.domain.room.usecase.UpdateMemberPositionUseCase
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.global.socket.vo.Peer
import dsm.wemeet.global.socket.vo.Signal
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    private val logger: Logger = LoggerFactory.getLogger(RoomWebSocketHandler::class.java)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val roomId = getRoomId(session)
        val peers = roomPeers.computeIfAbsent(roomId) { CopyOnWriteArrayList() }

        try {
            // 멤버가 이 방에 포함되고 있는지 확인
            checkIsMemberUseCase.execute(roomId, getUserEmail(session))

            // 이미 세션에 들어와있는지
            peers.find { getUserEmail(it) == getUserEmail(session) }?.let { throw AlreadyJoinedRoomException }
        } catch (e: WeMeetException) {
            session.close(CloseStatus(1008, "${e.errorCode.status}-${e.errorCode.message}")) // POLICY_VIOLATION
            return
        } catch (e: Exception) {
            session.close(CloseStatus.SERVER_ERROR)
            logger.error("유효 검증 중 예기치 못한 에러 발생", e)
            return
        }

        // 기존 멤버들에게 새로 참가하는 멤버 정보 전송
        val joinMsg = createMsg("join", objectMapper.writeValueAsString(session.toPeer()))
        peers.forEach { peer ->
            if (peer.isOpen) peer.sendMessage(TextMessage(joinMsg.toString()))
        }

        // 신규 피어에게 기존 멤버 정보 발송
        val existsPeerMsg = createMsg("exist", objectMapper.writeValueAsString(peers.map { it.toPeer() }))
        if (session.isOpen) session.sendMessage(TextMessage(existsPeerMsg.toString()))

        peers.add(session)
    }

    // 클라이언트에서 합주방 나가기 API와 함께 작동하므로 굳이 leaveRoomUseCase를 호출할 필요는 없음
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

        val signal = try {
            objectMapper.readValue(message.payload, Signal::class.java)
        } catch (e: Exception) {
            return
        }

        when (signal.type) {
            // WebRTC 연결 정보 관련 타입
            "offer", "answer", "candidate" -> {
                signal.to?.let { targetEmail ->
                    peers.find { it.attributes["email"] == targetEmail }
                        ?.takeIf { it.isOpen }
                        ?.sendMessage(
                            TextMessage(
                                objectMapper.writeValueAsString(
                                    signal.copy(
                                        from = getUserEmail(session)
                                    )
                                )
                            )
                        )
                }
            }
            // 강퇴
            "kick" -> {
                val currentEmail = getUserEmail(session)

                try {
                    kickMemberUseCase.execute(roomId, currentEmail, signal.to!!)
                } catch (e: Exception) {
                    return
                }

                peers.find { it.attributes["email"] == signal.to }
                    ?.takeIf { it.isOpen }
                    ?.close(CloseStatus(4003, "방장에게 강퇴당하셨습니다."))
            }
            // 포지션 변경
            "position" -> {
                signal.data?.let {
                    val position = try {
                        Position.valueOf(it.get("position").asText())
                    } catch (e: Exception) {
                        return
                    }
                    val mail = getUserEmail(session)

                    updateMemberPositionUseCase.execute(roomId, mail, position)

                    val positionMsg =
                        Signal(
                            type = signal.type,
                            data = objectMapper.createObjectNode().apply {
                                put("mail", mail)
                                put("position", position.name)
                            }
                        )

                    peers.forEach { peer ->
                        if (peer.isOpen && peer.attributes["email"]!!.toString() != mail) {
                            peer.sendMessage(TextMessage(objectMapper.writeValueAsString(positionMsg)))
                        }
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
            session.close(CloseStatus(1008, "${RoomNotFoundException.errorCode.status}-${RoomNotFoundException.errorCode.message}"))
            throw RoomNotFoundException
        }

    // 이미 AuthorizerInterceptor 에서 타입 확인을 해줬음
    private fun getUserEmail(session: WebSocketSession): String =
        session.attributes["email"]!!.toString()

    private fun getAccountId(session: WebSocketSession): String =
        session.attributes["accountId"]!!.toString()

    private fun getProfile(session: WebSocketSession): String? =
        (session.attributes["profile"]!! as Optional<String>).orElse(null)

    private fun createMsg(type: String, data: String): JSONObject =
        JSONObject()
            .put("type", type)
            .put("data", data)

    private fun WebSocketSession.toPeer() = Peer(
        getUserEmail(this),
        getAccountId(this),
        getProfile(this)
    )
}
