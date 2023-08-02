package com.dwiki.atmsimulation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFound(HttpServletRequest request, EntityNotFoundException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Entity not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message(message)
                .path(request.getRequestURI())
                .build()
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgument(HttpServletRequest request, RuntimeException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Illegal Argument";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .timestamp(new Date())
                .message(message)
                .path(request.getRequestURI())
                .build()
        );
    }
}
