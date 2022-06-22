package com.triple.club.Api.Util;


import com.triple.club.Api.Util.ApiResult.ApiResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private ApiResult result;
    private T data;

}
