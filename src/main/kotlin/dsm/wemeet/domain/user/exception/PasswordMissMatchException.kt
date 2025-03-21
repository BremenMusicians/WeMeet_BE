package dsm.wemeet.domain.user.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object PasswordMissMatchException : WeMeetException(
    ErrorCode.PASSWORD_MISS_MATCH
)
