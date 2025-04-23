package dsm.wemeet.domain.room.service

import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.user.repository.model.User

interface CheckRoomService {

    fun validateUserNotAlreadyInRoom(user: User, room: Room)

    fun validateRoomIsNotFull(room: Room)
}
