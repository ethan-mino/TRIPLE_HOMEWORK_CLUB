package com.triple.club.Api.Util.ApiResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiBasicResult implements ApiResult{
    INF_SUCCESS("INFO-201", "Successfully processed."),
    INF_NO_DATA("INFO-202", "No Data."),
    INF_DELETED("INFO-203", "Deleted."),

    ERR_INVALID_VALUE("ERROR-310", "Value is invalid. Please See the Request document."),
    ERR_DUP_ENTRY("ERROR-310", "Duplicate entry"),
    ERR_PERMISSION("ERROR-410", "You do not have permission."),
    ERR_NOT_EXIST("ERROR-420", "Data does not exist."),
    ERR_DB("ERROR-430", "DB ERROR."),

    ERR_SERVER("ERROR-500", "Internal Server Error. Please contact the customer center.");

    public final String code;
    public final String message;
}