package dsm.wemeet.global.s3.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object EmptyFileException : WeMeetException(
    ErrorCode.FILE_NOT_EXIST
)
