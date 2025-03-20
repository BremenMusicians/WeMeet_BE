package dsm.wemeet.domain.user.presentation.dto.request

import dsm.wemeet.domain.user.repository.model.Position
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignUpRequest(
    val email: String,

    @field:Size(min = 4, max = 20)
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[^\\s]*$", message = "비밀번호는 4~20자, 영문과 숫자를 포함해야 합니다.")
    val password: String,

    @field:Size(min = 1, max = 12)
    val accountId: String,

    val position: List<Position>
)
