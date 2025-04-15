package dsm.wemeet.domain.message.usecase

import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.message.presentation.dto.response.MessageResponse
import dsm.wemeet.domain.message.service.QueryMessageService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(readOnly = true)
@Service
class QueryMessageUseCase(
    private val queryMessageService: QueryMessageService,
    private val queryChatService: QueryChatService
) {

    fun execute(chatId: UUID): List<MessageResponse> {
        val chat = queryChatService.queryChatById(chatId)
        return queryMessageService.queryMessageListByChat(chat.id!!)
            .map {
                MessageResponse(
                    sender = it.sender.email,
                    content = it.content,
                    sendAt = it.sendAt
                )
            }.sortedBy { it.sendAt }
    }
}
