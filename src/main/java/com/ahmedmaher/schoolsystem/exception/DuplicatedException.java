package com.ahmedmaher.schoolsystem.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicatedException extends DataIntegrityViolationException {
    public DuplicatedException(String message) {
        super(message);
    }
}
