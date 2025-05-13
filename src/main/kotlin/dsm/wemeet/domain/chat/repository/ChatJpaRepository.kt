package dsm.wemeet.domain.chat.repository

import dsm.wemeet.domain.chat.repository.model.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface ChatJpaRepository : JpaRepository<Chat, UUID> {

    @Query(
        """
        SELECT c FROM Chat c
        WHERE (c.user1.email = :email1 AND c.user2.email = :email2)
        OR (c.user1.email = :email2 AND c.user2.email = :email1)
        """
    )
    fun findChatByUsers(
        @Param("email1") user1: String,
        @Param("email2") user2: String
    ): Chat?

    @Query(
        """
        SELECT c FROM Chat c
        WHERE c.user1.email = :email OR c.user2.email = :email
        ORDER BY c.lastSentAt DESC
        """
    )
    fun findChatsByUserOrderByRecent(@Param("email") user: String): List<Chat>

    @Query(
        """
    SELECT c FROM Chat c 
    WHERE 
        (c.user1.email = :userEmail AND c.user2.email IN :friendEmails) OR 
        (c.user2.email = :userEmail AND c.user1.email IN :friendEmails)
    """
    )
    fun findChatsBetweenUserAndFriends(
        @Param("userEmail") userEmail: String,
        @Param("friendEmails") friendEmails: List<String>
    ): List<Chat>
}
