package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object MemberNotFoundException : WeMeetException(
    ErrorCode.MEMBER_NOT_FOUND
)
