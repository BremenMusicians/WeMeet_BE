package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.service.CheckRoomService
import dsm.wemeet.domain.room.service.QueryRoomService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CheckIsMemberUseCase(
    private val queryUserService: QueryUserService,
    private val queryRoomService: QueryRoomService,
    private val checkMemberService: CheckRoomService
) {

    fun execute(roomId: UUID, userEmail: String) {
        val currentUser = queryUserService.queryUserByEmail(userEmail)
        val currentRoom = queryRoomService.queryRoomById(roomId)

        checkMemberService.validateUserIsInRoom(currentUser, currentRoom)
    }
}
