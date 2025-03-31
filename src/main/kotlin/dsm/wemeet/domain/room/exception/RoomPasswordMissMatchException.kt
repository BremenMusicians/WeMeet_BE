package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object RoomPasswordMissMatchException : WeMeetException(
    ErrorCode.ROOM_PASSWORD_MISS_MATCH
)
