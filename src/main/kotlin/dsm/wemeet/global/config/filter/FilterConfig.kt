package dsm.wemeet.global.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.wemeet.global.error.ExceptionFilter
import dsm.wemeet.global.jwt.JwtFilter
import dsm.wemeet.global.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class FilterConfig(
    private val objectMapper: ObjectMapper,
    private val jwtProvider: JwtProvider
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.addFilterBefore(ExceptionFilter(objectMapper), JwtFilter::class.java)
        http.addFilterBefore(JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)

        http.securityMatcher("/**")

        return http.build()
    }
}
