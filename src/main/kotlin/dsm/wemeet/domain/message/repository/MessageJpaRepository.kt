package dsm.wemeet.domain.message.repository

import dsm.wemeet.domain.message.repository.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MessageJpaRepository : JpaRepository<Message, UUID> {
    fun findAllByChatId(chatId: UUID): List<Message>
}
