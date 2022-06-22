package com.triple.club.Api.event;

import com.triple.club.Api.Util.ApiResponse;
import com.triple.club.Api.event.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @PostMapping("/events")
    public ResponseEntity<ApiResponse<EventDto>> event(@RequestBody EventDto eventDto){

        return null;
    }
}


/*

 */