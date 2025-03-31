package dsm.wemeet.domain.room.service.impl

import dsm.wemeet.domain.room.exception.AlreadyJoinedRoomException
import dsm.wemeet.domain.room.repository.MemberJpaRepository
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.service.CheckRoomService
import dsm.wemeet.domain.user.repository.model.User
import org.springframework.stereotype.Service

@Service
class CheckRoomServiceImpl(
    private val memberJpaRepository: MemberJpaRepository
) : CheckRoomService {

    override fun checkIsJoinedRoom(user: User, room: Room) {
        if (memberJpaRepository.existsMemberByUserAndRoom(user, room)) {
            throw AlreadyJoinedRoomException
        }
    }
}
