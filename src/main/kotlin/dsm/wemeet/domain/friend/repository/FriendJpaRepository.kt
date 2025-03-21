package dsm.wemeet.domain.friend.repository

import dsm.wemeet.domain.friend.repository.model.Friend
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface FriendJpaRepository : JpaRepository<Friend, UUID> {

    @Query(
        """
        SELECT f
        FROM Friend as f
        WHERE f.proposer.email = :user1 AND f.receiver.email = :user2 OR
            f.receiver.email = :user1 AND f.proposer.email = :user2
        """
    )
    fun findByAnyEmail(@Param("user1") user1: String, @Param("user2") user2: String): Friend?
}
