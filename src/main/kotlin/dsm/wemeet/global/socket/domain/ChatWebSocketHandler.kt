package dsm.wemeet.global.socket.domain

import dsm.wemeet.domain.message.usecase.SaveMessageUseCase
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
    private val saveMessageUseCase: SaveMessageUseCase
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

        val result = saveMessageUseCase.execute(senderId, toUserId, content)
        val receiverSession = sessionMap[toUserId]

        val responseJson = JSONObject()
            .put("sender", result.sender)
            .put("content", result.content)
            .put("sendAt", result.sendAt)

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
        return json.getString("receiver") to json.getString("content")
    }
}
