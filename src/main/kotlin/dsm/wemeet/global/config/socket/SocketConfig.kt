package dsm.wemeet.global.config.socket

import dsm.wemeet.global.socket.ChatWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class SocketConfig(
    private val chatWebSocketHandler: ChatWebSocketHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(chatWebSocketHandler, "/chat")
            .setAllowedOrigins("*")
        // TODO: Signaling
    }
}
