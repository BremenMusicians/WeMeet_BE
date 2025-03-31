package dsm.wemeet.global.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.Base64
import javax.crypto.SecretKey

@ConfigurationProperties("jwt")
class JwtProperties(
    secret: String,
    val accessExp: Long,
    val refreshExp: Long,
    val header: String,
    val prefix: String
) {
    val secret: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))
}
