package dsm.wemeet.domain.message.service

import dsm.wemeet.domain.chat.repository.model.Chat

interface DeleteMessageService {
    fun deleteAllByChat(chat: Chat)
}
