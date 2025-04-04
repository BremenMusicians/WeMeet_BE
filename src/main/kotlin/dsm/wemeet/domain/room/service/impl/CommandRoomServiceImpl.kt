package dsm.wemeet.domain.room.service.impl

import dsm.wemeet.domain.room.repository.MemberJpaRepository
import dsm.wemeet.domain.room.repository.RoomJpaRepository
import dsm.wemeet.domain.room.repository.model.Member
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.service.CommandRoomService
import org.springframework.stereotype.Service

@Service
class CommandRoomServiceImpl(
    private val roomJpaRepository: RoomJpaRepository,
    private val memberJpaRepository: MemberJpaRepository
) : CommandRoomService {

    override fun saveRoom(room: Room) =
        roomJpaRepository.save(room)

    override fun saveMember(member: Member) =
        memberJpaRepository.save(member)

    override fun deleteMember(member: Member) {
        memberJpaRepository.delete(member)
    }
}
