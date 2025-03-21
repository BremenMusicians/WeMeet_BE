package dsm.wemeet.domain.user.repository

import dsm.wemeet.domain.user.repository.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, String> {

    fun findByEmail(email: String): User?

    fun findByAccountId(accountId: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByAccountId(accountId: String): Boolean
}
