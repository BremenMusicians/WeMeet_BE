package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.friend.presentation.dto.response.FriendRequestListResponse
import dsm.wemeet.domain.friend.presentation.dto.response.FriendRequestResponse
import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.s3.S3Util
import org.springframework.stereotype.Service

@Service
class QueryMyFriendRequestListUseCase(
    private val queryUserService: QueryUserService,
    private val queryFriendService: QueryFriendService,
    private val s3Util: S3Util
) {

    fun execute(): FriendRequestListResponse {
        val user = queryUserService.getCurrentUser()
        val friendRequests = queryFriendService.queryFriendsByEmailAndAcceptance(user.email, false)
        val cnt = friendRequests.size

        val friendRequestsResponses = friendRequests.map {
            val opponent = getOpponent(it, user)
            FriendRequestResponse(
                friendId = it.id!!,
                accountId = opponent.accountId,
                profile = opponent.profile?.let { s3Util.generateUrl(it) },
                aboutMe = opponent.aboutMe,
                position = opponent.position.split(",").map { Position.valueOf(it) }
            )
        }

        return FriendRequestListResponse(friendRequests = friendRequestsResponses, requestCnt = cnt)
    }

    private fun getOpponent(friend: Friend, currentUser: User): User {
        return if (friend.proposer.email == currentUser.email) {
            friend.receiver
        } else {
            friend.proposer
        }
    }
}
