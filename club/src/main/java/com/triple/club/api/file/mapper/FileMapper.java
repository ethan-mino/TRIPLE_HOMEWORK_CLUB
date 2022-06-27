package com.triple.club.api.file.mapper;

import com.triple.club.api.file.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    FileEntity findById(String id);
    int save(FileEntity fileEntity);
    int updateById(FileEntity fileEntity);
    int deleteById(String id);
}
