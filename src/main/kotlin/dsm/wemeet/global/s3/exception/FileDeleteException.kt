package dsm.wemeet.global.s3.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object FileDeleteException : WeMeetException(
    ErrorCode.FILE_DELETE_ERROR
)
