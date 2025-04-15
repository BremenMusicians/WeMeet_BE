package dsm.wemeet.domain.message.presentation.dto.response

import java.time.LocalDateTime

data class MessageResponse(
    val sender: String,
    val content: String,
    val sendAt: LocalDateTime
)
