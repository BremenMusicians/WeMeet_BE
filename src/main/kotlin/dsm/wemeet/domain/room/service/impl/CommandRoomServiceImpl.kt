package dsm.wemeet.domain.room.service.impl

import dsm.wemeet.domain.room.repository.RoomJpaRepository
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.service.CommandRoomService
import org.springframework.stereotype.Service

@Service
class CommandRoomServiceImpl(
    private val roomJpaRepository: RoomJpaRepository
) : CommandRoomService {

    override fun saveRoom(room: Room) =
        roomJpaRepository.save(room)
}
