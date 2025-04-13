package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.exception.PasswordMissMatchException
import dsm.wemeet.domain.user.presentation.dto.request.SignInRequest
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.jwt.JwtProvider
import dsm.wemeet.global.jwt.dto.response.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserSignInUseCase(
    private val jwtProvider: JwtProvider,
    private val queryUserService: QueryUserService,
    private val passwordEncoder: PasswordEncoder
) {

    fun execute(request: SignInRequest): TokenResponse {
        val user = queryUserService.queryUserByEmail(request.mail)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordMissMatchException
        }

//        println(passwordEncoder.encode("jungjio45298114!"))

        return jwtProvider.generateToken(user.email)
    }
}
