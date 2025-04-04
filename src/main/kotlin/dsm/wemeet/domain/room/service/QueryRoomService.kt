package dsm.wemeet.domain.room.service

import dsm.wemeet.domain.room.repository.model.Member
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo
import java.util.UUID

interface QueryRoomService {

    fun queryRoomListByNameContainsAndOffsetByPage(page: Int, name: String?): List<CurrentRoomVo>

    fun queryRoomById(id: UUID): Room

    fun queryMemberByUserEmailAndRoomId(userEmail: String, roomId: UUID): Member

    fun queryAllMemberByRoomId(roomId: UUID): List<Member>

    fun countByNameContains(name: String?): Int
}
