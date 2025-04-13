package dsm.wemeet.global.socket

import dsm.wemeet.domain.chat.exception.ChatNotFoundException
import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.chat.service.SaveChatService
import dsm.wemeet.domain.message.repository.model.Message
import dsm.wemeet.domain.message.service.SaveMessageService
import dsm.wemeet.domain.user.repository.UserJpaRepository
import dsm.wemeet.domain.user.repository.model.User
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
class WebSocketHandler(
    private val userJpaRepository: UserJpaRepository,
    private val queryChatService: QueryChatService,
    private val saveChatService: SaveChatService,
    private val saveMessageService: SaveMessageService,
    private val jwtProvider: JwtProvider
) : TextWebSocketHandler() {

    private val sessionMap: MutableMap<String, WebSocketSession> = ConcurrentHashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = userIdFromSession(session)
        if (userId != null) {
            sessionMap[userId] = session
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionMap.entries.removeIf { it.value == session }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        val (toUserId, content) = parseMessage(payload)

        val senderId = userIdFromSession(session) ?: return
        val sender = userJpaRepository.findByEmail(senderId) ?: return
        val receiver = userJpaRepository.findByEmail(toUserId) ?: return

        val chat = getOrCreateChat(sender, receiver) ?: throw ChatNotFoundException

        val newMessage = Message(
            chat = chat,
            sender = sender,
            content = content
        )
        saveMessageService.save(newMessage)

        val receiverSession = sessionMap[toUserId]
        receiverSession?.takeIf { it.isOpen }?.sendMessage(TextMessage("Message received: $content"))
    }

    private fun userIdFromSession(session: WebSocketSession): String? {
        val authorization = session.handshakeHeaders.getFirst("Authorization") ?: return null
        val token = authorization.removePrefix("Bearer ").trim()
        return try {
            val claims = jwtProvider.getJws(token).body
            claims.subject
        } catch (e: Exception) {
            throw InvalidTokenException
        }
    }

    private fun parseMessage(payload: String): Pair<String, String> {
        val json = JSONObject(payload)
        return json.getString("to") to json.getString("content")
    }

    private fun getOrCreateChat(user1: User, user2: User): Chat? {
        val existingChat = queryChatService.queryChatByUser(user1.email, user2.email)
        return existingChat ?: saveChatService.save(Chat(user1 = user1, user2 = user2))
    }
}
