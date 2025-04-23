package dsm.wemeet.global.socket.intercept

import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.jwt.JwtProvider
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Exception

class AuthorizeInterceptor(
    private val jwtProvider: JwtProvider,
    private val queryUserService: QueryUserService
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
            attributes["email"] = mail
            attributes["accountId"] = queryUserService.queryUserByEmail(mail).accountId
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
