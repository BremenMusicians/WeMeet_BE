package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object InvalidTargetException : WeMeetException(
    ErrorCode.INVALID_TARGET_EXCEPTION
)
