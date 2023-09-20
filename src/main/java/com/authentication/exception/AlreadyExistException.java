package com.authentication.exception;

/**
 * @author Artur Tomeyan
 * @date 13/09/2023
 */
public class AlreadyExistException extends RuntimeException{

    public AlreadyExistException() {
        super();
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}