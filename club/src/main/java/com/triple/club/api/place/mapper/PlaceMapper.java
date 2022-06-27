package com.triple.club.api.place.mapper;

import com.triple.club.api.place.entity.PlaceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceMapper {
    List<PlaceEntity> findAll();
    PlaceEntity findById(String id);

    int save(PlaceEntity place);
    int updateById(PlaceEntity place);
    int deleteById(String id);
}
