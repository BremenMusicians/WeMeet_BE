package dsm.wemeet.global.jwt

import dsm.wemeet.global.jwt.dto.response.TokenResponse
import dsm.wemeet.global.jwt.exception.ExpireTokenException
import dsm.wemeet.global.jwt.exception.InvalidTokenException
import dsm.wemeet.global.jwt.repository.RefreshTokenJpaRepository
import dsm.wemeet.global.jwt.repository.model.RefreshToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService,
    private val refreshTokenRepository: RefreshTokenJpaRepository
) {

    companion object {
        private const val ACCESS_ = "access_token"
        private const val REFRESH = "refresh_token"
    }

    fun generateToken(accountId: String): TokenResponse {
        val accessToken = generateAccessToken(accountId, ACCESS_, jwtProperties.accessExp)
        val refreshToken = generateRefreshToken(REFRESH, jwtProperties.refreshExp)
        refreshTokenRepository.save(
            RefreshToken(accountId, refreshToken, jwtProperties.refreshExp)
        )
        return TokenResponse(accessToken, refreshToken)
    }

    fun reIssue(refreshToken: String): TokenResponse {
        if (!isRefreshToken(refreshToken)) {
            throw InvalidTokenException
        }

        refreshTokenRepository.findByToken(refreshToken)
            ?.let { token ->
                val id = token.id

                val tokenResponse = generateToken(id)
                token.update(tokenResponse.refreshToken, jwtProperties.refreshExp)
                return TokenResponse(tokenResponse.accessToken, tokenResponse.refreshToken)
            } ?: throw InvalidTokenException
    }

    private fun generateAccessToken(id: String, type: String, exp: Long): String =
        Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .signWith(jwtProperties.secret, SignatureAlgorithm.HS256)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    private fun generateRefreshToken(type: String, exp: Long): String =
        Jwts.builder()
            .setHeaderParam("typ", type)
            .signWith(jwtProperties.secret, SignatureAlgorithm.HS256)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    private fun isRefreshToken(token: String): Boolean =
        REFRESH == getJws(token).header["typ"].toString()

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.also {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length)
            }
        }

    fun authentication(token: String): Authentication? {
        val body: Claims = getJws(token).body
        val userDetails: UserDetails = getDetails(body)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getJws(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder().setSigningKey(jwtProperties.secret).build().parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw ExpireTokenException
        } catch (e: Exception) {
            throw InvalidTokenException
        }
    }

    private fun getDetails(body: Claims): UserDetails =
        userDetailsService.loadUserByUsername(body.subject)
}
