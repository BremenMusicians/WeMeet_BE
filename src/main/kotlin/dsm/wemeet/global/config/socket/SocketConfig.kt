package dsm.wemeet.global.config.socket

import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.jwt.JwtProvider
import dsm.wemeet.global.s3.S3Util
import dsm.wemeet.global.socket.domain.ChatWebSocketHandler
import dsm.wemeet.global.socket.domain.RoomWebSocketHandler
import dsm.wemeet.global.socket.intercept.AuthorizeInterceptor
import dsm.wemeet.global.socket.intercept.PathParsingInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.util.UriTemplate

@Configuration
@EnableWebSocket
class SocketConfig(
    private val chatWebSocketHandler: ChatWebSocketHandler,
    private val roomWebSocketHandler: RoomWebSocketHandler,
    private val jwtProvider: JwtProvider,
    private val queryUserService: QueryUserService,
    private val s3Util: S3Util
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
            .setAllowedOrigins("*")
        registry.addHandler(roomWebSocketHandler, "/ws/rooms/*")
            .setAllowedOrigins("*")
            .addInterceptors(
                AuthorizeInterceptor(jwtProvider, queryUserService, s3Util),
                PathParsingInterceptor(UriTemplate("/ws/rooms/{roomId}"))
            )
    }
}
