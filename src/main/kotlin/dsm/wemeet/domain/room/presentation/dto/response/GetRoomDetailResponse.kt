package dsm.wemeet.domain.room.presentation.dto.response

import dsm.wemeet.domain.user.repository.model.Position

data class GetRoomDetailResponse(
    val name: String,
    val info: String,
    val password: String?,
    val maxMember: Int,
    val owner: String,
    val members: List<MemberResponse>
)

data class MemberResponse(
    val mail: String,
    val position: Position?
)
