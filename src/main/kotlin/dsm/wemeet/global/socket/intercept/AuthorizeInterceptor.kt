package dsm.wemeet.global.socket.intercept

import dsm.wemeet.global.jwt.JwtProvider
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

class AuthorizeInterceptor(
    private val jwtProvider: JwtProvider
) : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val token = request.headers.getFirst("Authorization")
            ?.removePrefix("Bearer ")?.trim() ?: run {
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            return false
        }

        return try {
            attributes["email"] = jwtProvider.getJws(token).body.subject
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
