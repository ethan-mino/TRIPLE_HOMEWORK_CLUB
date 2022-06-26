package com.triple.club.api.file.mapper;

import com.triple.club.api.file.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    FileVO findById(String id);
    int save(FileVO fileVO);
    int updateById(FileVO fileVO);
    int deleteById(String id);
}
