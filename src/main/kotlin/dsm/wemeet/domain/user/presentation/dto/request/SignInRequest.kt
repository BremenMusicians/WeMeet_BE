package dsm.wemeet.domain.user.presentation.dto.request

data class SignInRequest(
    val email: String,
    val password: String
)
