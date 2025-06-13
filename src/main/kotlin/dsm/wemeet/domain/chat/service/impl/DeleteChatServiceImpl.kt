package dsm.wemeet.domain.chat.service.impl

import dsm.wemeet.domain.chat.repository.ChatJpaRepository
import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.chat.service.DeleteChatService
import org.springframework.stereotype.Service

@Service
class DeleteChatServiceImpl(
    private val chatJpaRepository: ChatJpaRepository
) : DeleteChatService {

    override fun delete(chat: Chat) {
        chatJpaRepository.delete(chat)
    }
}
