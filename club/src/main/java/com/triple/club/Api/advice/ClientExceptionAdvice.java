package com.triple.club.Api.advice;

import com.triple.club.Api.Util.ApiErrorResponse;
import com.triple.club.Api.exception.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionAdvice {
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiErrorResponse> invalidInputExceptionHandler(InvalidInputException ex){
        return null;
    }
}
