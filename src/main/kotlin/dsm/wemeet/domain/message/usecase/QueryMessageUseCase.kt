package dsm.wemeet.domain.message.usecase

import dsm.wemeet.domain.message.presentation.dto.response.MessageResponse
import dsm.wemeet.domain.message.service.QueryMessageService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(readOnly = true)
@Service
class QueryMessageUseCase(
    private val queryMessageService: QueryMessageService
) {

    fun execute(chatId: UUID): List<MessageResponse?> {
        val messages = queryMessageService.queryMessageListByChat(chatId)

        return messages?.map {
            MessageResponse(
                sender = it.sender.email,
                content = it.content,
                sendAt = it.sendAt
            )
        } ?: emptyList()
    }
}
