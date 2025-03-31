package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object RoomNotFoundException : WeMeetException(
    ErrorCode.ROOM_NOT_FOUND
)
