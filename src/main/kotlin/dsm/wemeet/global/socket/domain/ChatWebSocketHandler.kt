package dsm.wemeet.global.socket.domain

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.wemeet.domain.chat.usercase.QueryChatListUseCase
import dsm.wemeet.domain.message.usecase.SaveMessageUseCase
import dsm.wemeet.global.jwt.JwtProvider
import dsm.wemeet.global.jwt.exception.InvalidTokenException
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatWebSocketHandler(
    private val jwtProvider: JwtProvider,
    private val saveMessageUseCase: SaveMessageUseCase,
    private val queryChatListUseCase: QueryChatListUseCase
) : TextWebSocketHandler() {

    private val sessionMap: MutableMap<String, WebSocketSession> = ConcurrentHashMap()
    private val logger: Logger = LoggerFactory.getLogger(ChatWebSocketHandler::class.java)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        // 세션에서 사용자 ID를 추출하여 세션과 연결
        userIdFromSession(session)?.let { userId ->
            sessionMap[userId] = session
            sendChatListToUser(userId)
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionMap.entries.removeIf { it.value == session }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            val senderId = userIdFromSession(session) ?: return

            val (toUserId, content) = parseMessage(message.payload)

            val savedMessage = saveMessageUseCase.execute(senderId, toUserId, content)

            sendChatListToUser(senderId)
            sendChatListToUser(toUserId)

            val receiverSession = sessionMap[toUserId]
            val responseJson = JSONObject()
                .put("type", "MESSAGE")
                .put("sender", savedMessage.sender)
                .put("content", savedMessage.content)
                .put("sendAt", savedMessage.sendAt)

            receiverSession?.takeIf { it.isOpen }?.sendMessage(TextMessage(responseJson.toString()))
        } catch (e: Exception) {
            logger.error("💥메시지 전송중 에러 발생: ${session.id}", e)
            session.close(CloseStatus.SERVER_ERROR)
        }
    }

    // 사용자의 채팅 목록을 가져와서 해당 사용자에게 전송
    private fun sendChatListToUser(mail: String) {
        val session = sessionMap[mail] ?: return
        try {
            if (!session.isOpen) return

            val chatListResponse = queryChatListUseCase.execute(mail)

            val response = JSONObject()
                .put("type", "UPDATE_CHAT_LIST")
                .put("chats", JSONObject(ObjectMapper().writeValueAsString(chatListResponse.chats)))

            session.sendMessage(TextMessage(response.toString()))
        } catch (e: Exception) {
            logger.error("💥사용자 $mail 에게 채팅 목록을 전송하는 중 에러 발생", e)
            session.close(CloseStatus.SERVER_ERROR)
        }
    }

    private fun parseMessage(payload: String): Pair<String, String> {
        val json = JSONObject(payload) // 메시지 페이로드를 JSON 객체로 파싱
        return json.getString("receiver") to json.getString("content")
    }

    private fun userIdFromSession(session: WebSocketSession): String? {
        val token = session.uri?.let {
            UriComponentsBuilder.fromUri(it).build()
                .queryParams.getFirst("token")?.trim()
        } ?: return null

        return try {
            jwtProvider.getJws(token).body.subject
        } catch (e: Exception) {
            throw InvalidTokenException
        }
    }
}
