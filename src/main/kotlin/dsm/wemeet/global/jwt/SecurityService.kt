package dsm.wemeet.global.jwt

import dsm.wemeet.global.jwt.auth.UserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityService {

    fun getCurrentUserEmail(): String {
        return (SecurityContextHolder.getContext().authentication.principal as UserDetails).username
    }
}
