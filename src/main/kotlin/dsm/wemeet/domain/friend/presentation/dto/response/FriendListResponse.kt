package dsm.wemeet.domain.friend.presentation.dto.response

import dsm.wemeet.domain.friend.repository.model.IsFriendType
import dsm.wemeet.domain.user.repository.model.Position

data class FriendListResponse(
    val users: List<UserResponse>,
    val friendsCnt: Int
)

data class UserResponse(
    val accountId: String,
    val profile: String?,
    val aboutMe: String?,
    val position: List<Position>,
    val isFriend: IsFriendType
)
