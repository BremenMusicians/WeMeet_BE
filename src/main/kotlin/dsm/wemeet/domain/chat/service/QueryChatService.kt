package dsm.wemeet.domain.chat.service

import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.user.repository.model.User
import java.util.UUID

interface QueryChatService {

    fun queryChatByUser(user1: String, user2: String): Chat?

    fun queryChatById(id: UUID): Chat

    fun queryChatsByUserOrderByRecent(user: String): List<Chat>

    fun queryChatsByUserEmails(userEmail: String, friends: List<User>): Map<String, Chat?>
}
