package dsm.wemeet.domain.message.presentation.dto.response

data class MessageResponse(
    val sender: String,
    val content: String,
    val timestamp: Long
)
