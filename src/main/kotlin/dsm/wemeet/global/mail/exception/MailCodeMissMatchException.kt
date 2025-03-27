package dsm.wemeet.global.mail.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object MailCodeMissMatchException : WeMeetException(
    ErrorCode.MAIL_CODE_MISS_MATCH
)
