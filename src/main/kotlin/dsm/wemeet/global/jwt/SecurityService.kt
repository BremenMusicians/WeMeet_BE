package dsm.wemeet.global.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityService {

    fun getCurrentUserAccountId(): String {
        return (SecurityContextHolder.getContext().authentication.name)
    }
}
