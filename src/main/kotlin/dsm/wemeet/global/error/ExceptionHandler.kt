package dsm.wemeet.global.error

import WeMeetException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(WeMeetException::class)
    private fun handlingWeMeetException(e: WeMeetException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        return ResponseEntity(
            ErrorResponse(errorCode.status, errorCode.message),
            HttpStatus.valueOf(errorCode.status)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}
