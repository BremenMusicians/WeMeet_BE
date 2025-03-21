package dsm.wemeet.global.jwt.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object ExpireTokenException : WeMeetException(
    ErrorCode.EXPIRE_TOKEN_EXCEPTION
)
