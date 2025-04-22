package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.friend.exception.UnrelatedFriendRequestException
import dsm.wemeet.domain.friend.service.CommandFriendService
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class HandleFriendRequestUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService,
    private val commandFriendService: CommandFriendService
) {

    fun execute(friendId: UUID, status: Boolean) {
        val currentUser = queryUserService.getCurrentUser()
        val friendRequest = queryFriendService.queryFriendById(friendId)

        if (friendRequest.receiver.email != currentUser.email) {
            throw UnrelatedFriendRequestException
        }

        if (status) {
            friendRequest.isAccepted = true
        } else {
            commandFriendService.deleteFriend(friendRequest)
        }
    }
}
