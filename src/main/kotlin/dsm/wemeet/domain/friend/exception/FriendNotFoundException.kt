package dsm.wemeet.domain.friend.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object FriendNotFoundException : WeMeetException(
    ErrorCode.FRIEND_NOT_FOUND
)
