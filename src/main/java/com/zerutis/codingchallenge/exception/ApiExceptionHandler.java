package com.zerutis.codingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ApiException> handleInvalidArgument(InvalidArgumentException invalidArgument) {
        ApiException apiException = new ApiException(
                invalidArgument.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity(apiException, HttpStatus.BAD_REQUEST);
    }
}
