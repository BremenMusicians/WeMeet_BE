package dsm.wemeet.domain.message.presentation.dto.reseonse

data class MessageResponse(
    val from: String,
    val content: String,
    val timestamp: Long
)
