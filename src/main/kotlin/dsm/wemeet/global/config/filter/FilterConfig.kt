package dsm.wemeet.global.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.wemeet.global.error.ExceptionFilter
import dsm.wemeet.global.jwt.JwtFilter
import dsm.wemeet.global.jwt.JwtProvider
import org.springframework.security.config.annotation.SecurityConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class FilterConfig(
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper
) : SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    override fun init(builder: HttpSecurity?) {}

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(ExceptionFilter(objectMapper), JwtFilter::class.java)
    }
}
