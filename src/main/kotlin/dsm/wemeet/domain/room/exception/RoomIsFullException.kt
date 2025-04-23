package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object RoomIsFullException : WeMeetException(
    ErrorCode.ROOM_IS_FULL
)
