package dsm.wemeet.domain.message.service.impl

import dsm.wemeet.domain.message.repository.MessageJpaRepository
import dsm.wemeet.domain.message.service.QueryMessageService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class QueryMessageServiceImpl(
    private val messageJpaRepository: MessageJpaRepository
) : QueryMessageService {

    override fun queryMessageListByChat(chatId: UUID) =
        messageJpaRepository.findAllByChatId(chatId)

    override fun queryLastNullableMessageByChatId(chatId: UUID) =
        messageJpaRepository.findFirstByChatIdOrderBySendAtDesc(chatId)
}
