package dsm.wemeet.domain.chat.service

import dsm.wemeet.domain.chat.repository.model.Chat

interface DeleteChatService {

    fun delete(chat: Chat)
}
