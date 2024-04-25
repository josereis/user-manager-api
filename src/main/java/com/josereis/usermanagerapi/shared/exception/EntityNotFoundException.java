package com.josereis.usermanagerapi.shared.exception;

public class EntityNotFoundException extends jakarta.persistence.EntityNotFoundException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
