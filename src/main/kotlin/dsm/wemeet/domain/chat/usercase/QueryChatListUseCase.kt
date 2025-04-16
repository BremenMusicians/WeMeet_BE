package dsm.wemeet.domain.chat.usercase

import dsm.wemeet.domain.chat.presentation.dto.response.ChatListResponse
import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service

@Service
class QueryChatListUseCase(
    private val queryChatService: QueryChatService,
    private val queryUserService: QueryUserService
) {

    fun execute(): List<ChatListResponse> {
        val currentUser = queryUserService.getCurrentUser()

        return queryChatService.queryChatsByUserOrderByRecent(currentUser.email)
            .map {
                val user = getOpponent(it, currentUser)
                ChatListResponse(
                    chatId = it.id!!,
                    accountId = user.accountId,
                    profile = user.profile
                )
            }
    }

    private fun getOpponent(chat: Chat, currentUser: User): User {
        return if (chat.user1.email == currentUser.email) {
            chat.user2
        } else {
            chat.user1
        }
    }
}
