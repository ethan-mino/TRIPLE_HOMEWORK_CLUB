package com.triple.club.Api.user.advice;

import com.triple.club.Api.util.ApiCode;
import com.triple.club.Api.util.ApiError;
import com.triple.club.Api.util.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class AuthExceptionAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> AuthExceptionHandler(AuthenticationException ex){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();

        ApiError apiError = new ApiError();
        apiError.setCode(ApiCode.ERROR_INVALID_ACCOUNT);
        apiError.setMessage("Invalid Username or Password");
        apiErrorResponse.setError(apiError);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }
}