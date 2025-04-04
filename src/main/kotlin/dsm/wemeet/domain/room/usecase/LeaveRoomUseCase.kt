package dsm.wemeet.domain.room.usecase

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

    fun execute(roomId: UUID) {
        val currentUser = queryUserService.getCurrentUser()
        val currentRoom = queryRoomService.queryRoomById(roomId)

        // TODO : 이 더러운 코드는 추후 리팩토링 (#45)
        if (currentRoom.owner.email == currentUser.email) {
            val randomMember = queryRoomService.queryAllMemberByRoomId(currentRoom.id!!).random()

            currentRoom.owner = randomMember.user

            commandRoomService.deleteMember(randomMember)
        } else {
            val member = queryRoomService.queryMemberByUserEmailAndRoomId(
                userEmail = currentUser.email,
                roomId = currentRoom.id!!
            )

            commandRoomService.deleteMember(member)
        }
    }
}
