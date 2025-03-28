package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.friend.presentation.dto.response.FriendResponse
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.presentation.dto.response.MyPageResponse
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service

@Service
class UserMyPageUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService
) {

    fun execute(): MyPageResponse {
        val user = queryUserService.getCurrentUser()
        val friends = queryFriendService.queryFriendsByUserAndIsAccepted(user.email)

        val friendResponses = friends.map { it ->
            val friend = if (it.proposer == user) it.receiver else it.proposer
            FriendResponse(
                accountId = friend.accountId,
                profile = friend.profile,
                aboutMe = friend.aboutMe,
                position = friend.position.split(",").map { Position.valueOf(it) }
            )
        }

        return MyPageResponse(
            accountId = user.accountId,
            profile = user.profile,
            aboutMe = user.aboutMe,
            position = user.position.split(",").map { Position.valueOf(it) },
            friendsCnt = friendResponses.size,
            friends = friendResponses
        )
    }
}
