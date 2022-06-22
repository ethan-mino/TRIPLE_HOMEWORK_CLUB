package com.triple.club.Api.Util.ApiResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiAuthResult implements ApiResult{
    ERR_INVALID_ACCOUNT("ERROR-300", "Email or password is not valid.");

    public final String code;
    public final String message;
}
