package dsm.wemeet.domain.room.service

import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo

interface QueryRoomService {

    fun queryRoomListByNameContainsAndOffsetByPage(page: Int, name: String?): List<CurrentRoomVo>

    fun countByNameContains(name: String?): Int
}
