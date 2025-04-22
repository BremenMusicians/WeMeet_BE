package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.service.CommandRoomService
import dsm.wemeet.domain.room.service.QueryRoomService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class LeaveRoomUseCase(
    private val queryUserService: QueryUserService,
    private val queryRoomService: QueryRoomService,
    private val commandRoomService: CommandRoomService
) {

    fun execute(roomId: UUID, currentUserEmail: String? = null) {
        val currentUser = currentUserEmail?.let { queryUserService.queryUserByEmail(it) }
            ?: queryUserService.getCurrentUser()
        val currentRoom = queryRoomService.queryRoomById(roomId)

        val member = queryRoomService.queryMemberByUserEmailAndRoomId(
            userEmail = currentUser.email,
            roomId = currentRoom.id!!
        )

        commandRoomService.deleteMember(member)

        if (currentRoom.owner.email == currentUser.email) {
            ownerDelegation(currentRoom)
        }
    }

    private fun ownerDelegation(room: Room) {
        val members = queryRoomService.queryAllMemberByRoomIdOrderByJoinedAt(room.id!!)

        members.firstOrNull()?.let {
            room.owner = it.user
        } ?: run {
            commandRoomService.deleteRoom(room)
        }
    }
}
