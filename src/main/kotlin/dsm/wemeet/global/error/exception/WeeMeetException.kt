import dsm.wemeet.global.error.exception.ErrorCode

abstract class WeeMeetException(
    val errorCode: ErrorCode
) : RuntimeException()
