package dsm.wemeet.domain.chat.service.impl

import dsm.wemeet.domain.chat.exception.ChatNotFoundException
import dsm.wemeet.domain.chat.repository.ChatJpaRepository
import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.user.repository.model.User
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

    override fun queryChatsByUserOrderByRecent(user: String) =
        chatJpaRepository.findChatsByUserOrderByRecent(user)

    override fun queryChatsByUserEmails(userEmail: String, friends: List<User>): Map<String, Chat?> {
        val friendEmails = friends.map { it.email }
        val chats = chatJpaRepository.findChatsBetweenUserAndFriends(userEmail, friendEmails)

        return friendEmails.associateWith { email ->
            chats.find { chat ->
                (chat.user1.email == userEmail && chat.user2.email == email) ||
                    (chat.user1.email == userEmail && chat.user2.email == email)
            }
        }
    }
}
