package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.presentation.dto.request.CreateRoomRequest
import dsm.wemeet.domain.room.presentation.dto.response.CreateRoomResponse
import dsm.wemeet.domain.room.repository.model.Member
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.service.CommandRoomService
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateRoomUseCase(
    private val queryUserService: QueryUserService,
    private val commandRoomService: CommandRoomService
) {

    fun execute(request: CreateRoomRequest): CreateRoomResponse {
        val currentUser = queryUserService.getCurrentUser()

        val room = commandRoomService.saveRoom(
            Room(
                owner = currentUser,
                name = request.name,
                maxMember = request.maxMember,
                info = request.info,
                password = request.password
            )
        )

        val member = commandRoomService.saveMember(
            Member(
                room = room,
                user = currentUser,
            )
        )

        return CreateRoomResponse(room.id!!)
    }
}
