package com.triple.club.api.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private ApiCode code;
    private String message;
}
