package dsm.wemeet.domain.user.presentation.dto.request

import jakarta.validation.constraints.Email

data class SignInRequest(
    @field:Email
    val mail: String,

    val password: String
)
