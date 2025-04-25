package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.friend.presentation.dto.response.UserListResponse
import dsm.wemeet.domain.friend.presentation.dto.response.UserResponse
import dsm.wemeet.domain.friend.repository.model.IsFriendType
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.s3.S3Util
import org.springframework.stereotype.Service

@Service
class QueryMyFriendListUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService,
    private val s3Util: S3Util
) {

    fun execute(page: Int, name: String): UserListResponse {
        val currentUser = queryUserService.getCurrentUser()

        val users = queryFriendService.queryFriendUserListByEmailAndAccountIdContainsOffsetByPage(currentUser.email, name, page)
        val cnt = queryFriendService.countFriendsByEmailAndAccountIdContains(currentUser.email, name).toInt()

        val userResponse = users.map {
            UserResponse(
                accountId = it.accountId,
                profile = it.profile?.let(s3Util::generateUrl),
                aboutMe = it.aboutMe,
                position = it.position.split(",").map(Position::valueOf),
                isFriend = IsFriendType.FRIEND
            )
        }

        return UserListResponse(
            users = userResponse,
            usersCnt = cnt
        )
    }
}
