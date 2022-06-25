package com.triple.club.Api.place.mapper;

import com.triple.club.Api.place.vo.Place;
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
