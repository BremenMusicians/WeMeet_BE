package dsm.wemeet.domain.room.repository.vo

import java.util.UUID

data class CurrentRoomVo(
    val id: UUID,
    val name: String,
    val info: String,
    val isPublic: Boolean,
    val currentMember: Long,
    val maxMember: Int
)
