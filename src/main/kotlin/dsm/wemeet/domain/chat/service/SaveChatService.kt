package dsm.wemeet.domain.chat.service

import dsm.wemeet.domain.chat.repository.model.Chat

interface SaveChatService {

    fun save(chat: Chat): Chat?
}
