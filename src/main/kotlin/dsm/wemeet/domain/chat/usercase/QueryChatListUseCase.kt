package dsm.wemeet.domain.chat.usercase

import dsm.wemeet.domain.chat.presentation.dto.response.ChatListResponse
import dsm.wemeet.domain.chat.presentation.dto.response.ChatListResponses
import dsm.wemeet.domain.chat.repository.model.Chat
import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.message.service.QueryMessageService
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service

@Service
class QueryChatListUseCase(
    private val queryChatService: QueryChatService,
    private val queryUserService: QueryUserService,
    private val queryMessageService: QueryMessageService
) {

    fun execute(): ChatListResponses {
        val currentUser = queryUserService.getCurrentUser()

        val chats = queryChatService.queryChatsByUserOrderByRecent(currentUser.email)
            .map { chat ->
                val user = getOpponent(chat, currentUser)
                val lastMessage = queryMessageService.queryLastNullableMessageByChatId(chat.id!!)

                ChatListResponse(
                    chatId = chat.id!!,
                    accountId = user.accountId,
                    profile = user.profile,
                    position = user.position.split(",").map { Position.valueOf(it) },
                    lastMessage = lastMessage?.content
                )
            }

        return ChatListResponses(chats = chats)
    }

    private fun getOpponent(chat: Chat, currentUser: User): User {
        return if (chat.user1.email == currentUser.email) {
            chat.user2
        } else {
            chat.user1
        }
    }
}
