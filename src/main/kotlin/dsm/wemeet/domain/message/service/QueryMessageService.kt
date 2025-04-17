package dsm.wemeet.domain.message.service

import dsm.wemeet.domain.message.repository.model.Message
import java.util.UUID

interface QueryMessageService {
    fun queryMessageListByChat(chatId: UUID): List<Message>

    fun queryLastMessageByChatId(chatId: UUID): Message?
}
