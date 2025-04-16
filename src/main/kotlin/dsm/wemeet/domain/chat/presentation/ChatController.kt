package dsm.wemeet.domain.chat.presentation

import dsm.wemeet.domain.chat.presentation.dto.response.ChatListResponse
import dsm.wemeet.domain.chat.usercase.QueryChatListUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatController(
    private val queryChatListUseCase: QueryChatListUseCase
) {

    @GetMapping("/list")
    fun getChatList(): List<ChatListResponse> =
        queryChatListUseCase.execute()
}
