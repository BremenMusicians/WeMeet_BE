package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.exception.InvalidTargetException
import dsm.wemeet.domain.room.exception.IsNotOwnerException
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.service.CommandRoomService
import dsm.wemeet.domain.room.service.QueryRoomService
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class KickMemberUseCase(
    private val queryRoomService: QueryRoomService,
    private val queryUserService: QueryUserService,
    private val commandRoomUseCase: CommandRoomService
) {

    fun execute(roomId: UUID, accountId: String) {
        val currentUser = queryUserService.getCurrentUser()
        val currentRoom = queryRoomService.queryRoomById(roomId)
        val kickedUser = queryUserService.queryUserByAccountId(accountId)

        check(currentUser, currentRoom, kickedUser)

        val kickedMember = queryRoomService.queryMemberByUserEmailAndRoomId(
            userEmail = kickedUser.email,
            roomId = currentRoom.id!!
        )

        commandRoomUseCase.deleteMember(kickedMember)
    }

    private fun check(currentUser: User, currentRoom: Room, kickedUser: User) {
        if (currentUser.email != currentRoom.owner.email) {
            throw IsNotOwnerException
        }
        if (currentUser.email == kickedUser.email) {
            throw InvalidTargetException
        }
    }   
}
