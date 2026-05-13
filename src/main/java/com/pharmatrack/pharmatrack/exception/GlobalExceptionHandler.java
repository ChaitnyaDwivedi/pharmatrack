package com.pharmatrack.pharmatrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildResponse(String message, HttpStatus status, String path) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", path);
        return response;
    }

    @ExceptionHandler(MedicineNotFoundException.class)
    public ResponseEntity<?> handleMedicineNotFound(MedicineNotFoundException ex) {
        return new ResponseEntity<>(buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "/api/v1/medicines"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<?> handleOutOfStock(OutOfStockException ex) {
        return new ResponseEntity<>(buildResponse(ex.getMessage(), HttpStatus.CONFLICT, "/api/v1/medicines"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MedicineExpiredException.class)
    public ResponseEntity<?> handleExpired(MedicineExpiredException ex) {
        return new ResponseEntity<>(buildResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/medicines"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<?> handleInvalidQuantity(InvalidQuantityException ex) {
        return new ResponseEntity<>(buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, "/api/v1/medicines"), HttpStatus.BAD_REQUEST);
    }
}
