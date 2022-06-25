package com.triple.club.Api.place.service;

import com.triple.club.Api.place.mapper.PlaceMapper;
import com.triple.club.Api.place.vo.Place;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceService {
    private final PlaceMapper placeMapper;
    public PlaceService(PlaceMapper placeMapper){
        this.placeMapper = placeMapper;
    }

    @Transactional(readOnly = true)
    public List<Place> findAll(){return placeMapper.findAll();}

    @Transactional(readOnly = true)
    public Place findById(String id){return placeMapper.findById(id);}

    @Transactional
    public int save(Place place){return placeMapper.save(place);}

    @Transactional
    public int updateById(Place place){return placeMapper.updateById(place);}

    @Transactional
    public int deleteById(String id){return placeMapper.deleteById(id);}
}
