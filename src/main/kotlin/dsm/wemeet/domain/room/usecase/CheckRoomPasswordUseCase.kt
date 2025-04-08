package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.exception.RoomPasswordMissMatchException
import dsm.wemeet.domain.room.presentation.dto.request.CheckRoomPasswordRequest
import dsm.wemeet.domain.room.service.QueryRoomService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CheckRoomPasswordUseCase(
    private val queryRoomService: QueryRoomService
) {

    fun execute(roomId: UUID, request: CheckRoomPasswordRequest) {
        val currentRoom = queryRoomService.queryRoomById(roomId)

        currentRoom.password?.let {
            if (it != request.password) {
                throw RoomPasswordMissMatchException
            }
        }
    }
}
