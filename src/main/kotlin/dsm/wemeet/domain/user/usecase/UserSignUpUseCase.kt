package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.presentation.dto.request.SignUpRequest
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.domain.user.service.SaveUserService
import dsm.wemeet.global.jwt.JwtProvider
import dsm.wemeet.global.jwt.dto.response.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserSignUpUseCase(
    private val saveUserService: SaveUserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val queryUserService: QueryUserService
) {

    fun execute(request: SignUpRequest): TokenResponse {
        existUser(request.mail, request.accountId)

        val user = saveUserService.save(
            User(
                email = request.mail,
                password = passwordEncoder.encode(request.password),
                accountId = request.accountId,
                position = request.position.joinToString(",")
            )
        )
        return jwtProvider.generateToken(user.accountId)
    }

    private fun existUser(email: String, accountId: String) {
        queryUserService.existsByEmail(email)
        queryUserService.existsByAccountId(accountId)
    }
}
