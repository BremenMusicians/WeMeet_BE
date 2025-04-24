package dsm.wemeet.domain.friend.repository

import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.user.repository.model.User
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

    @Query(
        """
        SELECT f 
        FROM Friend f 
        WHERE (f.proposer.email = :user OR f.receiver.email = :user)
        AND f.isAccepted = true
    """
    )
    fun findAcceptedFriendsByUser(@Param("user") user: String): List<Friend>

    @Query(
        """
            SELECT CASE WHEN f.proposer.email = :email
                THEN f.receiver
                ELSE f.proposer
            END
            FROM Friend f
            WHERE (f.receiver.email = :email OR f.proposer.email = :email)
                AND f.isAccepted = TRUE
                AND CASE WHEN f.proposer.email = :email
                THEN f.receiver.accountId
                ELSE f.proposer.accountId
            END LIKE CONCAT('%', :accountId, '%')
        """
    )
    fun findFriendUsersByEmailAndContainsAccountId(@Param("email") email: String, @Param("accountId") accountId: String): List<User>
}
