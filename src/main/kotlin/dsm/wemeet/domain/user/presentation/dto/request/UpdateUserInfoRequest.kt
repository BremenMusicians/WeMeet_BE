package dsm.wemeet.domain.user.presentation.dto.request

import dsm.wemeet.domain.user.repository.model.Position
import jakarta.validation.constraints.Size

data class UpdateUserInfoRequest(
    @field:Size(min = 1, max = 12)
    val accountId: String,

    @field:Size(max = 50)
    val aboutMe: String?,

    val position: List<Position>
)
