package com.apod.exception;

import com.apod.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidDate(InvalidDateException ex) {
        log.warn("InvalidDate: {}", ex.getMessage());
        ApiResponse<Object> body = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ApodNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ApodNotFoundException ex) {
        log.warn("ApodNotFound: {}", ex.getMessage());
        ApiResponse<Object> body = ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .findFirst().orElse("Validation failed");
        log.warn("Validation failed: {}", msg);
        ApiResponse<Object> body = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse<Object>> handleDateParse(DateTimeParseException ex) {
        log.warn("Date parse error: {}", ex.getMessage());
        ApiResponse<Object> body = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Date must be in format YYYY-MM-DD");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ApiResponse<Object>> handleRestClient(RestClientException ex) {
        log.error("Upstream API error: {}", ex.getMessage(), ex);
        ApiResponse<Object> body = ApiResponse.error(HttpStatus.BAD_GATEWAY.value(), "Failed to retrieve data from NASA API");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAny(Exception ex) {
        log.error("Unhandled error: {}", ex.getMessage(), ex);
        ApiResponse<Object> body = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()
                ));
    }

}
