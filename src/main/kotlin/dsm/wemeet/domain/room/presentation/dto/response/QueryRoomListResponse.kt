package dsm.wemeet.domain.room.presentation.dto.response

import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo

data class QueryRoomListResponse(
    val rooms: List<CurrentRoomVo>,
    val roomCount: Int
)
