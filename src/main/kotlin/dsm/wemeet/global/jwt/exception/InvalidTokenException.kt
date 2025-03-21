package dsm.wemeet.global.jwt.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object InvalidTokenException : WeMeetException(
    ErrorCode.INVALID_TOKEN_EXCEPTION
)
