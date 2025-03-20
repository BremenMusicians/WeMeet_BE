package dsm.wemeet.global.jwt.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
