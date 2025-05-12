package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.chat.service.QueryChatService
import dsm.wemeet.domain.friend.presentation.dto.response.MyFriendListResponse
import dsm.wemeet.domain.friend.presentation.dto.response.MyFriendRequestResponse
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
    private val queryChatService: QueryChatService,
    private val s3Util: S3Util
) {

    fun execute(page: Int, name: String): MyFriendListResponse {
        val currentUser = queryUserService.getCurrentUser()

        val users = queryFriendService.queryFriendUserListByEmailAndAccountIdContainsOffsetByPage(currentUser.email, name, page)
        val cnt = queryFriendService.countFriendsByEmailAndAccountIdContains(currentUser.email, name).toInt()

        val friendsResponse = users.map {
            val chat = queryChatService.queryChatByUser(it.email, currentUser.email)

            MyFriendRequestResponse(
                mail = it.email,
                accountId = it.accountId,
                profile = it.profile?.let(s3Util::generateUrl),
                aboutMe = it.aboutMe,
                position = it.position.split(",").map(Position::valueOf),
                isFriend = IsFriendType.FRIEND,
                chatId = chat?.id
            )
        }

        return MyFriendListResponse(
            friends = friendsResponse,
            friendsCnt = cnt
        )
    }
}
