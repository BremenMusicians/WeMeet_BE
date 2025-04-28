package dsm.wemeet.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {

    BAD_REQUEST(400, "Bad Request"),
    BAD_FILE_EXTENSION(400, "Bad File Extension"),
    FILE_NOT_EXIST(400, "File Not Exist"),
    INVALID_TARGET_EXCEPTION(400, "Invalid Target Exception"),

    UNAUTHORIZED(401, "Unauthorized"),
    PASSWORD_MISS_MATCH(401, "Password Miss Match"),
    INVALID_TOKEN_EXCEPTION(401, "Invalid Token Exception"),
    EXPIRE_TOKEN_EXCEPTION(401, "Expire Token Exception"),
    MAIL_CODE_MISS_MATCH(401, "Mail Code Miss Match"),
    EXPIRED_MAIL_CODE_EXCEPTION(401, "Expired Mail Code Exception"),

    ROOM_PASSWORD_MISS_MATCH(403, "Room Password Miss Match"),
    IS_NOT_OWNER_EXCEPTION(403, "Is Not Owner Exception"),
    UNRELATED_FRIEND_REQUEST(403, "Unrelated Friend Request"),
    IS_NOT_FRIEND_EXCEPTION(403, "Is Not Friend Exception"),

    USER_NOT_FOUND(404, "User Not Found"),
    ROOM_NOT_FOUND(404, "Room Not Found"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    CHAT_NOT_FOUND(404, "Chat Not Found"),
    FRIEND_NOT_FOUND(404, "Friend Not Found"),

    USER_ALREADY_EXISTS(409, "User Already Exists"),
    FRIEND_ALREADY_EXISTS(409, "Friend Already Exists"),
    ALREADY_JOINED_ROOM(409, "Already Joined Room"),
    ROOM_IS_FULL(409, "Room Is Full"),

    INTERNAL_MAIL_SERVER_ERROR(500, "Internal Mail Server Error"),
    FILE_UPLOAD_ERROR(500, "File Upload Error"),
    FILE_DELETE_ERROR(500, "File Delete Error")
}
