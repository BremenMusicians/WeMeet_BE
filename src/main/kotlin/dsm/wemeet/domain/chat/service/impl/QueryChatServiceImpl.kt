package dsm.wemeet.domain.chat.service.impl

import dsm.wemeet.domain.chat.exception.ChatNotFoundException
import dsm.wemeet.domain.chat.repository.ChatJpaRepository
import dsm.wemeet.domain.chat.service.QueryChatService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class QueryChatServiceImpl(
    private val chatJpaRepository: ChatJpaRepository
) : QueryChatService {

    override fun queryChatByUser(user1: String, user2: String) =
        chatJpaRepository.findChatByUsers(user1, user2)

    override fun queryChatById(id: UUID) =
        chatJpaRepository.findByIdOrNull(id) ?: throw ChatNotFoundException
}
