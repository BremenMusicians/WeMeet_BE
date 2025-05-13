package dsm.wemeet.domain.friend.presentation.dto.response

import dsm.wemeet.domain.friend.repository.model.IsFriendType
import dsm.wemeet.domain.user.repository.model.Position
import java.util.UUID

data class MyFriendListResponse(
    val friends: List<MyFriendRequestResponse>,
    val friendsCnt: Int

)

data class MyFriendRequestResponse(
    val mail: String,
    val accountId: String,
    val profile: String?,
    val aboutMe: String?,
    val position: List<Position>,
    val isFriend: IsFriendType,
    val chatId: UUID?
)
