package ru.uds.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.uds.common.dto.ErrorResponse;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String message = exception.getMessage() != null ? exception.getMessage() : "Resource not found";

        return new ResponseEntity<>(new ErrorResponse(httpStatus, message), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = exception.getMessage() != null ? exception.getMessage() : "Illegal arguments";

        return new ResponseEntity<>(new ErrorResponse(httpStatus, message), httpStatus);
    }
}
