package dsm.wemeet.global.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.wemeet.global.error.ExceptionFilter
import dsm.wemeet.global.jwt.JwtFilter
import dsm.wemeet.global.jwt.JwtProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class FilterConfig(
    private val objectMapper: ObjectMapper,
    private val jwtProvider: JwtProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(ExceptionFilter(objectMapper), JwtFilter::class.java)
        builder.addFilterBefore(JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
    }
}
