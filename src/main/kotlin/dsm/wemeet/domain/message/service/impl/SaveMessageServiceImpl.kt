package dsm.wemeet.domain.message.service.impl

import dsm.wemeet.domain.message.repository.MessageJpaRepository
import dsm.wemeet.domain.message.repository.model.Message
import dsm.wemeet.domain.message.service.SaveMessageService
import org.springframework.stereotype.Service

@Service
class SaveMessageServiceImpl(
    private val messageJpaRepository: MessageJpaRepository
) : SaveMessageService {

    override fun save(message: Message) =
        messageJpaRepository.save(message)
}
