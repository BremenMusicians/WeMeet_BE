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

    fun execute(name: String): UserListResponse {
        val currentUser = queryUserService.getCurrentUser()

        val users = queryFriendService.queryFriendUserListByEmailContainsAccountId(currentUser.email, name)

        return users
            .map { user ->
                UserResponse(
                    accountId = user.accountId,
                    profile = user.profile?.let(s3Util::generateUrl),
                    aboutMe = user.aboutMe,
                    position = user.position.split(",").map(Position::valueOf),
                    isFriend = IsFriendType.FRIEND
                )
            }
            .let { list ->
                UserListResponse(
                    users = list,
                    usersCnt = list.size
                )
            }
    }
}
