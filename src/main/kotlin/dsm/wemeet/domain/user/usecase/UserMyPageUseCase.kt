package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.friend.presentation.dto.response.FriendResponse
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.presentation.dto.response.MyPageResponse
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.s3.S3Util
import org.springframework.stereotype.Service

@Service
class UserMyPageUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService,
    private val s3Util: S3Util
) {

    fun execute(): MyPageResponse {
        val user = queryUserService.getCurrentUser()
        val friends = queryFriendService.queryAcceptedFriendsByUser(user.email)
        val profileUrl = user.profile?.let { s3Util.generateUrl(it) }

        val friendResponses = friends.map { it ->
            val friend = if (it.proposer == user) it.receiver else it.proposer
            val friendProfile = friend.profile?.let { s3Util.generateUrl(it) }

            FriendResponse(
                accountId = friend.accountId,
                profile = friendProfile,
                aboutMe = friend.aboutMe,
                position = friend.position.split(",").map { Position.valueOf(it) }
            )
        }

        return MyPageResponse(
            accountId = user.accountId,
            profile = profileUrl,
            aboutMe = user.aboutMe,
            position = user.position.split(",").map { Position.valueOf(it) },
            friendsCnt = friendResponses.size,
            friends = friendResponses
        )
    }
}
