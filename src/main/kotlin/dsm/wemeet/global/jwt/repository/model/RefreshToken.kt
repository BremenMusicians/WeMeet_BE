package dsm.wemeet.global.jwt.repository.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@Entity
class RefreshToken(
    @Id
    val id: String,

    @Indexed
    var token: String,

    @TimeToLive
    var ttl: Long
) {
    fun update(token: String, ttl: Long) {
        this.token = token
        this.ttl = ttl
    }
}
