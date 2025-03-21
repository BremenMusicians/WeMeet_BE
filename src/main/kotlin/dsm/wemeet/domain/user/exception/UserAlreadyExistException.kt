package dsm.wemeet.domain.user.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object UserAlreadyExistException : WeMeetException(
    ErrorCode.USER_ALREADY_EXISTS
)
