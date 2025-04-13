package dsm.wemeet.domain.message.service

import dsm.wemeet.domain.message.repository.model.Message

interface SaveMessageService {
    fun save(message: Message): Message?
}
