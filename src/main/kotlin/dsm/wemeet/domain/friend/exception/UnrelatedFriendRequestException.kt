package dsm.wemeet.domain.friend.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object UnrelatedFriendRequestException : WeMeetException(
    ErrorCode.UNRELATED_FRIEND_REQUEST
)
