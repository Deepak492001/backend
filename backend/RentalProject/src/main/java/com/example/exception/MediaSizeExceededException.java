package com.example.exception;

public class MediaSizeExceededException extends RuntimeException {
    public MediaSizeExceededException(String message) {
        super(message);
    }

}
