package com.triple.club.Api.place.service;

import com.triple.club.Api.place.mapper.PlaceMapper;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private final PlaceMapper placeMapper;
    public PlaceService(PlaceMapper placeMapper){
        this.placeMapper = placeMapper;
    }

}
