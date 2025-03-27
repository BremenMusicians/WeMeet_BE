package dsm.wemeet.global.mail.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object ExpiredMailCodeException : WeMeetException(
    ErrorCode.EXPIRED_MAIL_CODE_EXCEPTION
)
