package com.example.exception;

public class MailNotSentException extends RuntimeException {
   public MailNotSentException(String message) {
        super(message);
    }

}
