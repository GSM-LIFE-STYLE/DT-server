package lifestyle.dt.global.error.handler

import lifestyle.dt.global.annotation.logger.Log4k
import lifestyle.dt.global.error.ErrorResponse
import lifestyle.dt.global.error.exception.BusinessException
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    @Log4k
    val logger: Logger? = null


    @ExceptionHandler(BusinessException::class)
    fun businessExceptionHandler(request: HttpServletRequest, ex: BusinessException): ResponseEntity<ErrorResponse> {
        logger?.error(request.requestURI)
        logger?.error(ex.message)
        val errorResponse = ErrorResponse(ex.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(ex.errorCode.code))
    }
}