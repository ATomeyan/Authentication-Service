package com.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Artur Tomeyan
 * @date 13/09/2023
 */
@ControllerAdvice
public class AdviserController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistException(AlreadyExistException e) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage()
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {

        return new ResponseEntity<>(
                new ExceptionResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        e.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ObjectInvalidException.class)
    public ResponseEntity<ExceptionResponse> handleObjectInvalidException(ObjectInvalidException e) {

        return new ResponseEntity<>(
                new ExceptionResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        e.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}