package dsm.wemeet.global.socket

import dsm.wemeet.domain.message.usecase.SendMessageUseCase
import dsm.wemeet.global.jwt.JwtProvider
import dsm.wemeet.global.jwt.exception.InvalidTokenException
import org.json.JSONObject
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatWebSocketHandler(
    private val jwtProvider: JwtProvider,
    private val sendMessageUseCase: SendMessageUseCase
) : TextWebSocketHandler() {

    private val sessionMap: MutableMap<String, WebSocketSession> = ConcurrentHashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        userIdFromSession(session)?.let { sessionMap[it] = session }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionMap.entries.removeIf { it.value == session }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val senderId = userIdFromSession(session) ?: return
        val (toUserId, content) = parseMessage(message.payload)

        val result = sendMessageUseCase.sendMessage(senderId, toUserId, content)
        val receiverSession = sessionMap[toUserId]

        val responseJson = JSONObject()
            .put("from", result.from)
            .put("content", result.content)
            .put("timestamp", result.timestamp)

        receiverSession?.takeIf { it.isOpen }?.sendMessage(TextMessage(responseJson.toString()))
    }

    private fun userIdFromSession(session: WebSocketSession): String? {
        val token = session.handshakeHeaders.getFirst("Authorization")
            ?.removePrefix("Bearer ")?.trim() ?: return null

        return try {
            jwtProvider.getJws(token).body.subject
        } catch (e: Exception) {
            throw InvalidTokenException
        }
    }

    private fun parseMessage(payload: String): Pair<String, String> {
        val json = JSONObject(payload)
        return json.getString("to") to json.getString("content")
    }
}
