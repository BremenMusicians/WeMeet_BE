package dsm.wemeet.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {

    PASSWORD_MISS_MATCH(401, "Password Miss Match"),
    INVALID_TOKEN_EXCEPTION(401, "Invalid Token Exception"),
    EXPIRE_TOKEN_EXCEPTION(401, "Expire Token Exception"),
    MAIL_CODE_MISS_MATCH(401, "Mail Code Miss Match"),
    EXPIRED_MAIL_CODE_EXCEPTION(401, "Expired Mail Code Exception"),

    ROOM_PASSWORD_MISS_MATCH(403, "Room Password Miss Match"),

    USER_NOT_FOUND(404, "User Not Found"),
    ROOM_NOT_FOUND(404, "Room Not Found"),

    USER_ALREADY_EXISTS(409, "User Already Exists"),
    FRIEND_ALREADY_EXISTS(409, "Friend Already Exists"),

    INTERNAL_MAIL_SERVER_ERROR(500, "Internal Mail Server Error")
}
