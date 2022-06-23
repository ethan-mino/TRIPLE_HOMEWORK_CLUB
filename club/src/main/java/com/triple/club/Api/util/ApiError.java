package com.triple.club.Api.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private ApiCode code;
    private String message;
}
