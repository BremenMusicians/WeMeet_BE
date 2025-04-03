package dsm.wemeet.global.s3.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object BadFileExtException : WeMeetException(
    ErrorCode.BAD_FILE_EXT
)
