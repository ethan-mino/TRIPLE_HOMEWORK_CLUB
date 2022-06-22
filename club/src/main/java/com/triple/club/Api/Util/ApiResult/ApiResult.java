package com.triple.club.Api.Util.ApiResult;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface ApiResult {
    String getCode();
    String getMessage();
}
