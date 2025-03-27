package dsm.wemeet.global.mail.presentation.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CheckMailRequest(
    @field:Email
    @field:NotBlank
    val mail: String,

    @field:Size(min = 4, max = 4)
    @field:NotBlank
    val code: String
)
