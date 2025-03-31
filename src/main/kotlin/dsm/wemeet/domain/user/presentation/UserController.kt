package dsm.wemeet.domain.user.presentation

import dsm.wemeet.domain.user.presentation.dto.request.SignInRequest
import dsm.wemeet.domain.user.presentation.dto.request.SignUpRequest
import dsm.wemeet.domain.user.presentation.dto.request.UpdateUserInfoRequest
import dsm.wemeet.domain.user.usecase.ExistAccountIdUseCase
import dsm.wemeet.domain.user.usecase.UpdateUserInfoUseCase
import dsm.wemeet.domain.user.usecase.UserMyPageUseCase
import dsm.wemeet.domain.user.usecase.UserSignInUseCase
import dsm.wemeet.domain.user.usecase.UserSignUpUseCase
import dsm.wemeet.domain.user.usecase.UserTokenRefreshUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userSignUpUseCase: UserSignUpUseCase,
    private val userSignInUseCase: UserSignInUseCase,
    private val userMyPageUseCase: UserMyPageUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val existAccountIdUseCase: ExistAccountIdUseCase,
    private val userTokenRefreshUseCase: UserTokenRefreshUseCase
) {

    @PostMapping("/signUp")
    fun signUp(
        @RequestBody @Valid
        request: SignUpRequest
    ) =
        userSignUpUseCase.execute(request)

    @PostMapping("/signIn")
    fun signIn(
        @RequestBody @Valid
        request: SignInRequest
    ) =
        userSignInUseCase.execute(request)

    @PatchMapping("/refresh")
    fun refresh(@RequestHeader("X-Refresh-Token") token: String) = userTokenRefreshUseCase.execute(token)

    @GetMapping("/myPage")
    fun myPage() = userMyPageUseCase.execute()

    @GetMapping("/exist/{account-id}")
    fun existAccountId(@PathVariable(name = "account-id") accountId: String) = existAccountIdUseCase.execute(accountId)

    @PatchMapping("/update")
    fun updateUserInfo(
        @RequestBody @Valid
        request: UpdateUserInfoRequest
    ) = updateUserInfoUseCase.execute(request)
}
