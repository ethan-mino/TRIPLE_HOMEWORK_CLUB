package com.triple.club.Api.util;

public enum ApiCode {
    ERROR_INVALID_ACCOUNT(500),

    ERR_INVALID_VALUE(501),
    ERR_DUP_ENTRY(502),

    ERR_SERVER(503),
    ERR_DB(504);

    private final int codeValue;
    ApiCode(int codeValue){
        this.codeValue = codeValue;
    }
}
