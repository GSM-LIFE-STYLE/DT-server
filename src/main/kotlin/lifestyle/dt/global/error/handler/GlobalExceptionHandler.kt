package lifestyle.dt.global.error.handler

import lifestyle.dt.global.annotation.Log4K
import lifestyle.dt.global.error.ErrorResponse
import lifestyle.dt.global.error.exception.DTException
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    @Log4K
    val log: Logger? = null

    @ExceptionHandler(DTException::class)
    fun basicExceptionHandler(request: HttpServletRequest, ex: DTException): ResponseEntity<ErrorResponse> {
        log?.error(request.requestURI)
        log?.error(ex.message)
        val errorResponse = ErrorResponse(ex.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(ex.errorCode.code))
    }

}