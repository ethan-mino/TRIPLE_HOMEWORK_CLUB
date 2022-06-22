package com.triple.club.Api.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DuplicateUsernameException extends RuntimeException{
    private String username;
}
