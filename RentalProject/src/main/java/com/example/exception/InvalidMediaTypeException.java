package com.example.exception;

public class InvalidMediaTypeException extends RuntimeException {
    public InvalidMediaTypeException(String message) {
        super(message);
    }

}
