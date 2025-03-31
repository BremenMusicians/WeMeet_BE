package dsm.wemeet.domain.room.service

import dsm.wemeet.domain.room.repository.model.Member
import dsm.wemeet.domain.room.repository.model.Room

interface CommandRoomService {

    fun saveRoom(room: Room): Room

    fun saveMember(member: Member): Member
}
