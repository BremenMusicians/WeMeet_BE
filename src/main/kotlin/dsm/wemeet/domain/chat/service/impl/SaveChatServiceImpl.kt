package dsm.wemeet.domain.chat.service.impl

import dsm.wemeet.domain.chat.repository.ChatJpaRepository
import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.chat.service.SaveChatService
import org.springframework.stereotype.Service

@Service
class SaveChatServiceImpl(
    private val chatJpaRepository: ChatJpaRepository
) : SaveChatService {

    override fun save(chat: Chat) =
        chatJpaRepository.save(chat)
}
