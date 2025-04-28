package dsm.wemeet.domain.friend.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object IsNotFriendException : WeMeetException(
    ErrorCode.IS_NOT_FRIEND_EXCEPTION
)
