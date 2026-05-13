package com.pharmatrack.pharmatrack.exception;

public class MedicineExpiredException extends RuntimeException {
    public MedicineExpiredException(String message) {
        super(message);
    }
}
