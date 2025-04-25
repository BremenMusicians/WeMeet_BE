package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.friend.presentation.dto.response.UserListResponse
import dsm.wemeet.domain.friend.presentation.dto.response.UserResponse
import dsm.wemeet.domain.friend.repository.model.IsFriendType
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.s3.S3Util
import org.springframework.stereotype.Service

@Service
class QueryUserListUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService,
    private val s3Util: S3Util
) {
    fun execute(page: Int, name: String): UserListResponse {
        val user = queryUserService.getCurrentUser()
        val users = queryUserService.queryUserListByAccountIdContainsAndOffsetByPage(page, name)
        val cnt = queryUserService.countUsersByAccountIdContains(name).toInt()

        val returnUsers = users.map {
            val isFriend = getFriendStatus(user, it)

            UserResponse(
                accountId = it.accountId,
                profile = it.profile?.let { s3Util.generateUrl(it) },
                aboutMe = it.aboutMe,
                position = it.position.split(",").map { Position.valueOf(it) },
                isFriend = isFriend
            )
        }

        return UserListResponse(users = returnUsers, usersCnt = cnt)
    }

    private fun getFriendStatus(user: User, otherUser: User): IsFriendType {
        val friendRequest = queryFriendService.queryNullableFriendRequestByEmails(user.email, otherUser.email)

        if (user == otherUser) {
            return IsFriendType.FRIEND
        }

        return if (friendRequest != null) {
            if (friendRequest.isAccepted) {
                IsFriendType.FRIEND
            } else {
                IsFriendType.WAITING
            }
        } else {
            IsFriendType.NOT_FRIEND
        }
    }
}
