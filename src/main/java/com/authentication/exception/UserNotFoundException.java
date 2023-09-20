package com.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Artur Tomeyan
 * @date 06/09/2023
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}