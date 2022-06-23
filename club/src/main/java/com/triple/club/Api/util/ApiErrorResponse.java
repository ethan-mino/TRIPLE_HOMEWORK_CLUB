package com.triple.club.Api.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ApiErrorResponse extends ApiResponse{
    ApiError error;
}
