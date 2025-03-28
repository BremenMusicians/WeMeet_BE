package dsm.wemeet.domain.friend.presentation.dto.response

import dsm.wemeet.domain.user.repository.model.Position

data class FriendResponse(
    val accountId: String,
    val profile: String?,
    val aboutMe: String?,
    val position: List<Position>
)
