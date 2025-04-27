package dsm.wemeet.domain.friend.presentation.dto.response

import dsm.wemeet.domain.user.repository.model.Position
import java.util.UUID

data class FriendRequestListResponse(
    val friendRequests: List<FriendRequestResponse>,
    val requestCnt: Int
)

data class FriendRequestResponse(
    val friendId: UUID,
    val accountId: String,
    val profile: String?,
    val aboutMe: String?,
    val position: List<Position>
)
