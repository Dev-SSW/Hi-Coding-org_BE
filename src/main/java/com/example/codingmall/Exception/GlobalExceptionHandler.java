package com.example.codingmall.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SerialNumberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSerialNumberNotFound(SerialNumberNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "SERIAL_NUMBER_NOT_FOUND"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "USER_NOT_FOUND"));
    }

    @ExceptionHandler(AlreadyHasPlantRoleException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyHasRole(AlreadyHasPlantRoleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "ALREADY_PLANT_ROLE"));
    }

    @ExceptionHandler(UserHasNotAnyOrderException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyHasRole(UserHasNotAnyOrderException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "USER_HAS_NOT_ANY_ORDER"));
    }
}
