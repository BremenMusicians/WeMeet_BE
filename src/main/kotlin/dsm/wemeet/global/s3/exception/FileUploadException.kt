package dsm.wemeet.global.s3.exception

import WeMeetException
import dsm.wemeet.global.error.exception.ErrorCode

object FileUploadException : WeMeetException(
    ErrorCode.FILE_UPLOAD_ERROR
)
