package dsm.wemeet.global.jwt.auth

import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UserDetailService(
    private val queryUserService: QueryUserService
) : UserDetailsService {

    override fun loadUserByUsername(accountId: String): UserDetails {
        val user = queryUserService.queryUserByAccountId(accountId)
            ?: throw Exception("Invalid Token")

        return UserDetails(user.accountId)
    }
}
