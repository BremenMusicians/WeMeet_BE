package dsm.wemeet.domain.chat.presentation.dto.response

import dsm.wemeet.domain.user.repository.model.Position
import java.util.UUID

data class ChatListResponses(
    val chats: List<ChatListResponse>
)

data class ChatListResponse(
    val chatId: UUID,
    val accountId: String,
    val profile: String?,
    val position: List<Position>,
    val lastMessage: String?
)
