package dsm.wemeet.global.jwt.auth

import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.jwt.exception.InvalidTokenException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UserDetailService(
    private val queryUserService: QueryUserService
) : UserDetailsService {

    override fun loadUserByUsername(accountId: String): UserDetails {
        val user = queryUserService.queryUserByAccountId(accountId)
            ?: throw InvalidTokenException

        return UserDetails(user.accountId)
    }
}
