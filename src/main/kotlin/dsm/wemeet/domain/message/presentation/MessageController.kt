package dsm.wemeet.domain.message.presentation

import dsm.wemeet.domain.message.usecase.QueryMessageUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/message")
class MessageController(
    private val queryMessageUseCase: QueryMessageUseCase
) {

    @GetMapping("/{chat-id}")
    fun getMessage(@PathVariable("chat-id") chatId: UUID) =
        queryMessageUseCase.execute(chatId)
}
