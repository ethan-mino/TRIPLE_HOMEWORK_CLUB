package com.triple.club.Api.user.advice;

import com.triple.club.Api.user.exception.DuplicateUsernameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionAdvice {

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> duplicateUsernameHandler(DuplicateUsernameException ex){
        String msg = ex.getUsername() + "은 이미 존재하는 아이디입니다.";
        return ResponseEntity.ok(msg);
    }

}
