package dsm.wemeet.domain.room.presentation.dto.request

import jakarta.annotation.Nullable
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class CreateRoomRequest(

    @field:Size(min = 1, max = 20)
    val name: String,

    @field:Min(2)
    @field:Max(5)
    val maxMember: Int,

    @field:Size(min = 1, max = 100)
    val info: String,

    @field:Nullable
    @field:Size(min = 4, max = 4)
    val password: String?
)
