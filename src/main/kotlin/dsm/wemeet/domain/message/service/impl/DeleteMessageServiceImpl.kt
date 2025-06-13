package dsm.wemeet.domain.message.service.impl

import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.message.repository.MessageJpaRepository
import dsm.wemeet.domain.message.service.DeleteMessageService
import org.springframework.stereotype.Service

@Service
class DeleteMessageServiceImpl(
    private val messageJpaRepository: MessageJpaRepository
) : DeleteMessageService {

    override fun deleteAllByChat(chat: Chat) {
        chat.id?.let { messageJpaRepository.deleteAllByChatId(it) }
    }
}
