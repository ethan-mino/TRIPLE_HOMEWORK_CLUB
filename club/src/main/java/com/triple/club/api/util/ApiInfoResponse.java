package com.triple.club.api.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiInfoResponse<T> extends ApiResponse{
    T data;
}
