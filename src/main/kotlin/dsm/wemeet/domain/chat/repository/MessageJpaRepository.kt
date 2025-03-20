package dsm.wemeet.domain.chat.repository

import dsm.wemeet.domain.chat.repository.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MessageJpaRepository : JpaRepository<Message, UUID>
