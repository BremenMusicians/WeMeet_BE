package dsm.wemeet.domain.user.repository

import dsm.wemeet.domain.user.repository.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, String>
