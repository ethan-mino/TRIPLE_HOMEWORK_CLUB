package com.triple.club.api.advice;

import com.triple.club.api.util.ApiCode;
import com.triple.club.api.util.ApiError;
import com.triple.club.api.util.ApiErrorResponse;
import com.triple.club.api.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionAdvice {
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiErrorResponse> invalidInputExceptionHandler(InvalidInputException ex){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();

        ApiError apiError = new ApiError();
        apiError.setCode(ApiCode.ERR_INVALID_VALUE);
        apiError.setMessage("Invalid Parameter.");
        apiErrorResponse.setError(apiError);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }
}
