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
import java.time.LocalDateTime

@Service
@Transactional
class SaveMessageUseCase(
    private val queryUserService: QueryUserService,
    private val queryChatService: QueryChatService,
    private val saveChatService: SaveChatService,
    private val saveMessageService: SaveMessageService
) {

    fun execute(sender: String, receiver: String, content: String): MessageResponse {
        val sendUser = queryUserService.queryUserByEmail(sender)
        val receiveUser = queryUserService.queryUserByEmail(receiver)

        val chat = queryChatService.queryChatByUser(sendUser.email, receiveUser.email)
            ?: saveChatService.save(Chat(user1 = sendUser, user2 = receiveUser))

        val message = Message(chat = chat, sender = sendUser, content = content)

        val sendTime = LocalDateTime.now()

        saveMessageService.save(message)
        chat.lastSentAt = sendTime

        return MessageResponse(sendUser.email, content, LocalDateTime.now())
    }
}
