package dsm.wemeet.domain.friend.repository

import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.user.repository.model.User
import org.springframework.data.domain.Pageable
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
        WHERE f.receiver.email = :email
        AND f.isAccepted = :isAccepted
    """
    )
    fun findFriendsByEmailAndIsAccepted(@Param("email") email: String, @Param("isAccepted") isAccepted: Boolean): List<Friend>

    @Query(
        value = """
        (
            SELECT u.*
            FROM user u
            JOIN friend f
            ON f.proposer_email = :email
            AND f.receiver_email = u.email
            WHERE f.is_accepted = TRUE
            AND u.account_id LIKE CONCAT('%', :accountId, '%')
        )
        UNION
        (
            SELECT u.*
            FROM user u
            JOIN friend f
            ON f.receiver_email = :email
            AND f.proposer_email = u.email
            WHERE  f.is_accepted = TRUE
            AND u.account_id LIKE CONCAT('%', :accountId, '%')
        )
        LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
        """,
        nativeQuery = true
    )
    fun findFriendUsersByEmailAndAccountIdContainsOffsetByPage(@Param("email") email: String, @Param("accountId") accountId: String, pageable: Pageable): List<User>

    @Query(
        """
            SELECT COUNT(f)
            FROM Friend f
            WHERE (f.receiver.email = :email OR f.proposer.email = :email)
                AND f.isAccepted = TRUE
                AND CASE WHEN f.proposer.email = :email
                THEN f.receiver.accountId
                ELSE f.proposer.accountId
            END LIKE CONCAT('%', :accountId, '%')
        """
    )
    fun countFriendsByEmailAndAccountIdContains(@Param("email") email: String, @Param("accountId") accountId: String): Long
}
