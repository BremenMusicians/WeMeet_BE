package dsm.wemeet.global.mail.presentation.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SendMailRequest(
    @field:Email
    @field:NotBlank
    val mail: String
)
