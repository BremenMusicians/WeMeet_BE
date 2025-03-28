package dsm.wemeet.domain.user.presentation.dto.response

import dsm.wemeet.domain.friend.presentation.dto.response.FriendResponse
import dsm.wemeet.domain.user.repository.model.Position

data class MyPageResponse(
    val accountId: String,
    val profile: String?,
    val aboutMe: String?,
    val position: List<Position>,
    val friendsCnt: Int,
    val friends: List<FriendResponse>
)
