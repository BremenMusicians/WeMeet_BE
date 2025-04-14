package dsm.wemeet.domain.message.usecase

import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.chat.service.SaveChatService
import dsm.wemeet.domain.message.presentation.dto.response.MessageResponse
import dsm.wemeet.domain.message.repository.model.Message
import dsm.wemeet.domain.message.service.SaveMessageService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SendMessageUseCase(
    private val queryUserService: QueryUserService,
    private val queryChatService: QueryChatService,
    private val saveChatService: SaveChatService,
    private val saveMessageService: SaveMessageService
) {

    fun sendMessage(sender: String, receiver: String, content: String): MessageResponse {
        val sender = queryUserService.queryUserByEmail(sender)
        val receiver = queryUserService.queryUserByEmail(receiver)

        val chat = queryChatService.queryChatByUser(sender.email, receiver.email)
            ?: saveChatService.save(Chat(user1 = sender, user2 = receiver))

        val message = Message(chat = chat, sender = sender, content = content)
        saveMessageService.save(message)

        return MessageResponse(sender.email, content, System.currentTimeMillis())
    }
}
