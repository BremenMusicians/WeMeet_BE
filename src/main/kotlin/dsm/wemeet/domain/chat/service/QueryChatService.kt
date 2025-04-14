package dsm.wemeet.domain.chat.service

import dsm.wemeet.domain.chat.repository.model.Chat

interface QueryChatService {

    fun queryChatByUser(user1: String, user2: String): Chat?
}
