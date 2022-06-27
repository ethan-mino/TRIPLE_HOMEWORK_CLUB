package com.triple.club.api.place.service;

import com.triple.club.api.place.mapper.PlaceMapper;
import com.triple.club.api.place.entity.PlaceEntity;
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
    public List<PlaceEntity> findAll(){return placeMapper.findAll();}

    @Transactional(readOnly = true)
    public PlaceEntity findById(String id){return placeMapper.findById(id);}

    @Transactional
    public int save(PlaceEntity place){return placeMapper.save(place);}

    @Transactional
    public int updateById(PlaceEntity place){return placeMapper.updateById(place);}

    @Transactional
    public int deleteById(String id){return placeMapper.deleteById(id);}
}
