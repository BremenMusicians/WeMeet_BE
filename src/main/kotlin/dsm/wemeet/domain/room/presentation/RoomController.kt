package dsm.wemeet.domain.room.presentation

import dsm.wemeet.domain.room.presentation.dto.request.CreateRoomRequest
import dsm.wemeet.domain.room.presentation.dto.response.CreateRoomResponse
import dsm.wemeet.domain.room.usecase.CreateRoomUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val createRoomUseCase: CreateRoomUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createRoom(
        @RequestBody @Valid
        request: CreateRoomRequest
    ): CreateRoomResponse {
        return createRoomUseCase.execute(request)
    }
}
