package dsm.wemeet.domain.user.repository

import dsm.wemeet.domain.user.repository.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserJpaRepository : JpaRepository<User, String> {

    fun findByEmail(email: String): User?

    fun findByAccountId(accountId: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByAccountId(accountId: String): Boolean

    @Query(
        """
            SELECT u FROM User u WHERE (:accountId IS NULL OR u.accountId LIKE %:accountId%)
            """
    )
    fun findAllByAccountIdContaining(@Param("accountId") accountId: String?, pageable: Pageable): List<User>
}
