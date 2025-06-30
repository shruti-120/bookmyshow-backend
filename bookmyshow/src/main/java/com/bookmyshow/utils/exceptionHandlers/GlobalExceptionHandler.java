package com.bookmyshow.utils.exceptionHandlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bookmyshow.dtos.error.ErrorResponse;
import com.bookmyshow.enums.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error("Resource not found", ex);
        return buildResponse(ErrorCode.RESOURCE_NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException ex) {
        log.warn("Invalid token: {}", ex.getMessage());
        return buildResponse(ErrorCode.INVALID_TOKEN, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
        log.error("Illegal State", ex);
        return buildResponse(ErrorCode.ILLEGAL_STATE_EXCEPTION, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("Validation Error", ex);
        return buildResponse(ErrorCode.VALIDATION_FAILED, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AuthenticationFailedException.class, AuthenticationException.class, SecurityException.class })
    public ResponseEntity<ErrorResponse> handleAuthExceptions(Exception ex) {
        log.warn("Authentication failed", ex);
        return buildResponse(ErrorCode.UNAUTHORIZED, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Internal Server Error", ex);
        return buildResponse(ErrorCode.INTERNAL_ERROR,
                             "Something went wrong. Please try again later.",
                             HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to build the standard error response
    private ResponseEntity<ErrorResponse> buildResponse(ErrorCode code,
                                                        String message,
                                                        HttpStatus status) {
        ErrorResponse body = new ErrorResponse(
            code.name(),
            message,
            status.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(body, status);
    }
}
