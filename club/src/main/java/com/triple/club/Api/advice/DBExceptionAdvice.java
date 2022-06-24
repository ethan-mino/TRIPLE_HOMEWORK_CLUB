package com.triple.club.Api.advice;

import com.triple.club.Api.util.ApiCode;
import com.triple.club.Api.util.ApiError;
import com.triple.club.Api.util.ApiErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class DBExceptionAdvice {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiErrorResponse> SQLExceptionHandler(SQLException ex){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();

        ApiError apiError = new ApiError();
        apiError.setCode(ApiCode.ERR_DB);
        apiError.setMessage("DB ERROR.");
        apiErrorResponse.setError(apiError);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiErrorResponse> duplicateKeyExceptionHandler(DuplicateKeyException ex){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();

        ApiError apiError = new ApiError();
        apiError.setCode(ApiCode.ERR_INVALID_VALUE);
        apiError.setMessage("Duplicate Key Exception.");
        apiErrorResponse.setError(apiError);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }
}