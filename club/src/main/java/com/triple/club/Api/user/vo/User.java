package com.triple.club.Api.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@ToString
public class User {
    private String id;

    @NotBlank
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$\n")
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date updatedAt;
}
