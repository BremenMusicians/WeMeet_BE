package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.presentation.dto.response.GetRoomDetailResponse
import dsm.wemeet.domain.room.presentation.dto.response.MemberResponse
import dsm.wemeet.domain.room.service.CheckRoomService
import dsm.wemeet.domain.room.service.QueryRoomService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetRoomDetailUseCase(
    private val queryUserService: QueryUserService,
    private val queryRoomService: QueryRoomService,
    private val checkRoomService: CheckRoomService
) {

    fun execute(roomId: UUID): GetRoomDetailResponse {
        val currentUser = queryUserService.getCurrentUser()
        val currentRoom = queryRoomService.queryRoomById(roomId)

        checkRoomService.validateUserIsInRoom(currentUser, currentRoom)

        val members = queryRoomService.queryAllMemberByRoomIdOrderByJoinedAt(roomId)

        return GetRoomDetailResponse(
            name = currentRoom.name,
            info = currentRoom.info,
            password = currentRoom.password,
            maxMember = currentRoom.maxMember,
            owner = currentRoom.owner.email,
            members = members.map { member ->
                MemberResponse(
                    mail = member.user.email,
                    position = member.position
                )
            }
        )
    }
}
