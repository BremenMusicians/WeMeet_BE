package dsm.wemeet.domain.room.presentation

import dsm.wemeet.domain.room.presentation.dto.request.CreateRoomRequest
import dsm.wemeet.domain.room.presentation.dto.response.CreateRoomResponse
import dsm.wemeet.domain.room.presentation.dto.response.QueryRoomListResponse
import dsm.wemeet.domain.room.usecase.CreateRoomUseCase
import dsm.wemeet.domain.room.usecase.QueryRoomListUseCase
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val createRoomUseCase: CreateRoomUseCase,
    private val queryRoomListUseCase: QueryRoomListUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createRoom(
        @RequestBody @Valid
        request: CreateRoomRequest
    ): CreateRoomResponse {
        return createRoomUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun queryRoomList(
        @RequestParam(value = "page")
        @Min(0)
        page: Int,
        @RequestParam(value = "name", required = false) name: String?
    ): QueryRoomListResponse {
        return queryRoomListUseCase.execute(page, name)
    }
}
