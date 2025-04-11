package dsm.wemeet.domain.room.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object IsNotOwnerException : WeMeetException(
    ErrorCode.IS_NOT_OWNER_EXCEPTION
)
