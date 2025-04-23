package dsm.wemeet.global.socket.intercept

import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.jwt.JwtProvider
import dsm.wemeet.global.s3.S3Util
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Exception
import java.util.Optional

class AuthorizeInterceptor(
    private val jwtProvider: JwtProvider,
    private val queryUserService: QueryUserService,
    private val s3Util: S3Util
) : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val token = UriComponentsBuilder.fromUri(request.uri).build()
            .queryParams.getFirst("token")
            ?.removePrefix("Bearer ")?.trim() ?: run {
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            return false
        }

        return try {
            val mail = jwtProvider.getJws(token).body.subject
            val currentUser = queryUserService.queryUserByEmail(mail)

            attributes["email"] = currentUser.email
            attributes["accountId"] = currentUser.accountId
            attributes["profile"] = Optional.ofNullable(currentUser.profile?.let { s3Util.generateUrl(it) })
            true
        } catch (e: Exception) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            false
        }
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
    }
}
