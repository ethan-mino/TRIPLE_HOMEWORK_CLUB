package com.triple.club.api.advice;

import com.triple.club.api.util.ApiCode;
import com.triple.club.api.util.ApiError;
import com.triple.club.api.util.ApiErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class DBExceptionAdvice {
    @ExceptionHandler({SQLException.class, TransactionException.class})
    public ResponseEntity<ApiErrorResponse> DBExceptionHandler(Exception ex){
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