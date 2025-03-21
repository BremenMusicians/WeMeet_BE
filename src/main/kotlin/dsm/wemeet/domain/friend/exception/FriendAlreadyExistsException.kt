package dsm.wemeet.domain.friend.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object FriendAlreadyExistsException : WeMeetException(
    ErrorCode.FRIEND_ALREADY_EXISTS
)
