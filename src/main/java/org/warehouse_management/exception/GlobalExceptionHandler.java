package org.warehouse_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.warehouse_management.dto.error.ErrorResponseDto;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPassword(InvalidPasswordException ex) {
        return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExists(AlreadyExistsException ex) {
        return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return buildError(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex) {
        return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        return buildError("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDto> buildError(String message, HttpStatus status) {

        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(message);
        error.setStatus(status.value());
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(status).body(error);
    }
}