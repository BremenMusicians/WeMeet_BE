package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object UserIsNotMemberException : WeMeetException(
    ErrorCode.USER_IS_NOT_MEMBER
)
