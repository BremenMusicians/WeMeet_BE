package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.service.QueryRoomService
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UpdateMemberPositionUseCase(
    private val queryRoomService: QueryRoomService,
    private val queryUserService: QueryUserService
) {

    fun execute(roomId: UUID, userEmail: String, position: Position) {
        val currentRoom = queryRoomService.queryRoomById(roomId)
        val currentUser = queryUserService.queryUserByEmail(userEmail)

        val currentMember = queryRoomService.queryMemberByUserEmailAndRoomId(currentUser.email, currentRoom.id!!)

        currentMember.position = position
    }
}
