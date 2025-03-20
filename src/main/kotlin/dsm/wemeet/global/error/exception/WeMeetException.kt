import dsm.wemeet.global.error.exception.ErrorCode

abstract class WeMeetException(
    val errorCode: ErrorCode
) : RuntimeException()
