package dsm.wemeet.global.jwt.repository

import dsm.wemeet.global.jwt.repository.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenJpaRepository : JpaRepository<RefreshToken, String> {

    fun findByToken(token: String): RefreshToken?
}
