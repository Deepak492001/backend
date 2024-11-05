package com.example.exception;


public class MediaNotUploadException extends RuntimeException {

    public MediaNotUploadException(String message) {
        super(message);
    }

}
