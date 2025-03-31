package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object AlreadyJoinedRoomException : WeMeetException(
    ErrorCode.ALREADY_JOINED_ROOM
)
