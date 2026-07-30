package com.grow.smartpark.exception;

import com.grow.smartpark.constants.SmartParkConstants;
import com.grow.smartpark.domain.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2(topic = "global-exception")
public class GlobalExceptionHandler {

    /**
     * Handle validation errors (e.g. @NotNull, @Size).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.error("Validation error: {}", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                        .message("Validation failed: " + errors)
                        .data(null)
                        .build()
        );
    }

    /**
     * Handle entity not found exceptions.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFound(EntityNotFoundException ex) {
        log.error("Entity not found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                        .message(ex.getMessage())
                        .data(null)
                        .build()
        );
    }

    /**
     * Handle runtime exceptions (business logic errors).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime exception: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                        .message(ex.getMessage())
                        .data(null)
                        .build()
        );
    }

    /**
     * Handle all other exceptions (fallback).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                        .message("An unexpected error occurred.")
                        .data(null)
                        .build()
        );
    }
}
