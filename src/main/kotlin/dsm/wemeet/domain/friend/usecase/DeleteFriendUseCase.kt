package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.chat.service.DeleteChatService
import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.friend.service.CommandFriendService
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.message.service.DeleteMessageService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteFriendUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService,
    private val commandFriendService: CommandFriendService,
    private val queryChatService: QueryChatService,
    private val deleteChatService: DeleteChatService,
    private val deleteMessageService: DeleteMessageService
) {

    fun execute(friendAccountId: String) {
        val currentUser = queryUserService.getCurrentUser()
        val deletedFriendUser = queryUserService.queryUserByAccountId(friendAccountId)

        val currentFriend = queryFriendService.queryAcceptedFriendByEmails(currentUser.email, deletedFriendUser.email)

        val chat = queryChatService.queryChatByUser(currentUser.email, deletedFriendUser.email)
        chat?.let {
            deleteMessageService.deleteAllByChat(it)
            deleteChatService.delete(it)
        }

        commandFriendService.deleteFriend(currentFriend)
    }
}
