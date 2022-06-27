package com.triple.club.api.file.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FileEntity {
    private String id;
    private String ownerId;
    private String url;
    private String contentType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;

    @Builder
    public FileEntity(String id, String ownerId, String url, String contentType, Date createdAt, Date updatedAt){
        this.id = id;
        this.ownerId = ownerId;
        this.url = url;
        this.contentType = contentType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
