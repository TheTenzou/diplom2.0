package ru.thetenzou.tsoddservice.common.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.thetenzou.tsoddservice.common.dto.ErrorResponse
import javax.persistence.EntityNotFoundException

/**
 * controller advice required for handling exceptions
 */
@ControllerAdvice
class ErrorControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    fun notFoundException(exception: Exception) = ResponseEntity(
        ErrorResponse(HttpStatus.NOT_FOUND, exception.message ?: "Resource not found"),
        HttpStatus.NOT_FOUND,
    )

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(exception: Exception) = ResponseEntity(
        ErrorResponse(HttpStatus.BAD_REQUEST, exception.message ?: "IllegalArguments"),
        HttpStatus.BAD_REQUEST,
    )
}