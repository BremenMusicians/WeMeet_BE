package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.friend.service.CheckFriendService
import dsm.wemeet.domain.friend.service.CommandFriendService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class RequestFriendUseCase(
    private val queryUserService: QueryUserService,
    private val checkFriendService: CheckFriendService,
    private val commandFriendService: CommandFriendService
) {

    fun execute(accountId: String) {
        val proposal = queryUserService.getCurrentUser()
        val receiver = queryUserService.queryUserByAccountId(accountId)

        checkFriendService.checkIsFriend(proposal, receiver)

        commandFriendService.saveFriend(
            Friend(
                id = UUID.randomUUID(),
                proposer = proposal,
                receiver = receiver,
                isAccepted = false
            )
        )
    }
}
