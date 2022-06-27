package com.triple.club.api.place.mapper;

import com.triple.club.api.place.entity.Place;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceMapper {
    List<Place> findAll();
    Place findById(String id);

    int save(Place place);
    int updateById(Place place);
    int deleteById(String id);
}
