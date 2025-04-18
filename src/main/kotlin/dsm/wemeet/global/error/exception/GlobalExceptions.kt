package dsm.wemeet.global.error.exception

import WeMeetException

object UnauthorizedException : WeMeetException(
    ErrorCode.UNAUTHORIZED
)

object BadRequestException : WeMeetException(
    ErrorCode.BAD_REQUEST
)
