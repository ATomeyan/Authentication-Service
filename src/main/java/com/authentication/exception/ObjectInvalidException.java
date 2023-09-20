package com.authentication.exception;

public class ObjectInvalidException extends RuntimeException{
    public ObjectInvalidException() {
        super();
    }

    public ObjectInvalidException(String message) {
        super(message);
    }
}