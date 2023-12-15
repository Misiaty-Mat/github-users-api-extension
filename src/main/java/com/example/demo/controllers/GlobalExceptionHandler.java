package com.example.demo.controllers;

import com.example.demo.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return getNotFoundErrorResponse(ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleHttpClientErrorExceptionNotFound(HttpClientErrorException.NotFound ex) {
        return getNotFoundErrorResponse("User with this login does not exist");
    }

    private static ErrorResponse getNotFoundErrorResponse(String exceptionMessage) {
        return ErrorResponse.builder()
                .message("404 Not Found - " + exceptionMessage)
                .build();
    }
}
