package dsm.wemeet.domain.room.presentation.dto.request

import jakarta.validation.constraints.Size

data class JoinRoomRequest(

    @Size(min = 4, max = 4)
    val password: String
)
