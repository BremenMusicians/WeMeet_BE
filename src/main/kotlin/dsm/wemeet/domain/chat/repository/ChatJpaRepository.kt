package dsm.wemeet.domain.chat.repository

import dsm.wemeet.domain.chat.repository.model.Chat
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ChatJpaRepository : JpaRepository<Chat, UUID>
