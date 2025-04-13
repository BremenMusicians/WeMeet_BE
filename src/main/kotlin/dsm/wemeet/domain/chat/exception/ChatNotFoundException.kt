package dsm.wemeet.domain.chat.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object ChatNotFoundException : WeMeetException(
    ErrorCode.CHAT_NOT_FOUND
)
