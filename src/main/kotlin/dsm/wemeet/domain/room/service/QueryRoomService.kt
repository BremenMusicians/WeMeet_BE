package dsm.wemeet.domain.room.service

import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo

interface QueryRoomService {

    fun queryRoomListByNameAndOffsetByPage(page: Int, name: String?): List<CurrentRoomVo>

    fun queryRoomCount(): Int
}
