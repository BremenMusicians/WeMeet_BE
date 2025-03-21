package dsm.wemeet.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {

    PASSWORD_MISS_MATCH(401, "Password Miss Match"),

    USER_NOT_FOUND(404, "User Not Found"),

    USER_ALREADY_EXISTS(409, "User already exists"),
    FRIEND_ALREADY_EXISTS(409, "Friend Already Exists"),
}
