package dsm.wemeet.domain.user.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object UserNotFoundException : WeMeetException(
    ErrorCode.USER_NOT_FOUND
)
