package dsm.wemeet.global.mail.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object InternalMailServerException : WeMeetException(
    ErrorCode.INTERNAL_MAIL_SERVER_ERROR
)
