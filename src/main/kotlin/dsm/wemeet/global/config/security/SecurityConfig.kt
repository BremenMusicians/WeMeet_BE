package dsm.wemeet.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.wemeet.global.config.filter.FilterConfig
import dsm.wemeet.global.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper
) {

    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(HttpMethod.POST, "/user/signUp").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/signIn").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/refresh").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/user/profile").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/user/update").authenticated()
                .requestMatchers(HttpMethod.GET, "/user/myPage").authenticated()
                .requestMatchers(HttpMethod.GET, "/user/exist/{account-id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/accountId").authenticated()
                .requestMatchers(HttpMethod.GET, "/user").authenticated()
                .requestMatchers(HttpMethod.POST, "/mail").permitAll()
                .requestMatchers(HttpMethod.POST, "/mail/check").permitAll()
                .requestMatchers(HttpMethod.GET, "/friends").authenticated()
                .requestMatchers(HttpMethod.POST, "/friends/{friend-id}").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/friends/request/{friend-id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/friends/my").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/friends/{friend-id}").authenticated()
                .requestMatchers(HttpMethod.POST, "/rooms").authenticated()
                .requestMatchers(HttpMethod.POST, "/rooms/{room-id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/rooms").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/rooms/{room-id}").authenticated()
                .requestMatchers(HttpMethod.POST, "/rooms/password/{room-id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/rooms/{room-id}/members/{account-id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/chat/list").authenticated()
                .requestMatchers(HttpMethod.GET, "/message/{chat-id}").authenticated()
                .anyRequest().denyAll()
        }
            .apply(FilterConfig(jwtProvider, objectMapper))

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers("/ws/**")
        }
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf(
            "http://localhost:3000",
            "http://127.0.0.1:3000",
            "https://we-meet-fe.vercel.app"
        )
        configuration.allowedHeaders = listOf("*")
        configuration.allowedMethods = listOf("POST", "GET", "DELETE", "PUT", "PATCH")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
