package dsm.wemeet.domain.chat.presentation.dto.response

import java.util.UUID

data class ChatListResponse(
    val chatId: UUID,
    val accountId: String,
    val profile: String?
)
