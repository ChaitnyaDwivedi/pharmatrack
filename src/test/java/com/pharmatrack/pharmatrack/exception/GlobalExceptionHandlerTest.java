package com.pharmatrack.pharmatrack.exception;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleMedicineNotFoundException() {

        MedicineNotFoundException exception = new MedicineNotFoundException("Medicine not found");

        ResponseEntity<?> response = handler.handleMedicineNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);

        assertEquals("Medicine not found", body.get("message"));

        assertEquals(404, body.get("status"));
    }

    @Test
    void shouldHandleOutOfStockException() {

        OutOfStockException exception = new OutOfStockException("Out of stock");

        ResponseEntity<?> response = handler.handleOutOfStock(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);

        assertEquals("Out of stock", body.get("message"));

        assertEquals(409, body.get("status"));
    }

    @Test
    void shouldHandleMedicineExpiredException() {

        MedicineExpiredException exception = new MedicineExpiredException("Medicine expired");

        ResponseEntity<?> response = handler.handleExpired(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);

        assertEquals("Medicine expired", body.get("message"));

        assertEquals(422, body.get("status"));
    }

    @Test
    void shouldHandleInvalidQuantityException() {

        InvalidQuantityException exception = new InvalidQuantityException("Invalid quantity");

        ResponseEntity<?> response = handler.handleInvalidQuantity(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body);

        assertEquals("Invalid quantity", body.get("message"));

        assertEquals(400, body.get("status"));
    }
}