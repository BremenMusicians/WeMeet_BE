package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.exception.RoomPasswordMissMatchException
import dsm.wemeet.domain.room.presentation.dto.request.JoinRoomRequest
import dsm.wemeet.domain.room.repository.model.Member
import dsm.wemeet.domain.room.service.CheckRoomService
import dsm.wemeet.domain.room.service.CommandRoomService
import dsm.wemeet.domain.room.service.QueryRoomService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class JoinRoomUseCase(
    private val queryRoomService: QueryRoomService,
    private val queryUserService: QueryUserService,
    private val checkRoomService: CheckRoomService,
    private val commandRoomService: CommandRoomService
) {

    fun execute(roomId: UUID, request: JoinRoomRequest) {
        val currentRoom = queryRoomService.queryRoomById(roomId)
        val currentUser = queryUserService.getCurrentUser()

        if (currentRoom.password != request.password) {
            throw RoomPasswordMissMatchException
        }

        checkRoomService.checkIsJoinedRoom(currentUser, currentRoom)

        val member = commandRoomService.saveMember(
            Member(
                room = currentRoom,
                user = currentUser
            )
        )

        // TODO : WebRTC 시그널링 API
    }
}
