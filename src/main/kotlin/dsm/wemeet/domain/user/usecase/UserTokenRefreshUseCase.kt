package dsm.wemeet.domain.user.usecase

import dsm.wemeet.global.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserTokenRefreshUseCase(
    private val jwtProvider: JwtProvider
) {
    fun execute(token: String) =
        jwtProvider.reIssue(token)
}
