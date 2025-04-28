package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.friend.exception.IsNotFriendException
import dsm.wemeet.domain.friend.service.CommandFriendService
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteFriendUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService,
    private val commandFriendService: CommandFriendService
) {

    fun execute(friendAccountId: String) {
        val currentUser = queryUserService.getCurrentUser()
        val deletedFriendUser = queryUserService.queryUserByAccountId(friendAccountId)

        val currentFriend = queryFriendService.queryAcceptedFriendByEmails(currentUser.email, deletedFriendUser.email)

        if (!currentFriend.isAccepted) throw IsNotFriendException

        commandFriendService.deleteFriend(currentFriend)
    }
}
