package com.triple.club.Api.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiInfoResponse<T> extends ApiResponse{
    T data;
}
