package com.triple.club.api.place.Controller;

import com.triple.club.api.exception.InvalidInputException;
import com.triple.club.api.place.service.PlaceService;
import com.triple.club.api.place.entity.Place;
import com.triple.club.api.user.dto.CustomUserDetails;
import com.triple.club.api.util.ApiInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @GetMapping("") // 모든 장소 조회
    public ResponseEntity<ApiInfoResponse<List<Place>>> getAllPlace(){
        ApiInfoResponse<List<Place>> apiResponse = new ApiInfoResponse<>();

        List<Place> places = placeService.findAll();

        if(places.size() == 0){  // 장소가 하나도 존재하지 않은 경우
            return ResponseEntity.notFound().build();
        }else{  // 해당 id의 장소가 존재하는 경우
            apiResponse.setData(places);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @GetMapping("/{placeId}")    // 장소 조회 by id
    public ResponseEntity<ApiInfoResponse<Place>> getPlaceById(@PathVariable("placeId") String placeId){
        ApiInfoResponse<Place> apiResponse = new ApiInfoResponse<>();

        Place place = placeService.findById(placeId);
        if(place == null){  // 해당 id의 장소가 없는 경우
            return ResponseEntity.notFound().build();
        }else{  // 해당 id의 장소가 존재하는 경우
            apiResponse.setData(place);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @PostMapping("")    // 장소 생성
    public ResponseEntity<ApiInfoResponse<Place>> createPlace(@AuthenticationPrincipal CustomUserDetails user,
                                                              @Valid @RequestBody Place place,
                                                              BindingResult bindingResult) throws SQLException {
        if(bindingResult.hasErrors()){
            throw new InvalidInputException();
        }

        ApiInfoResponse<Place> apiResponse = new ApiInfoResponse<>();
        place.setWriterId(user.getId());  // place의 owner 설정

        int createCnt = placeService.save(place);
        if(createCnt == 1){
            Place createdPlace = placeService.findById(place.getId());  // 생성된 place 조회
            apiResponse.setData(createdPlace);
            return ResponseEntity.ok(apiResponse);  // 200 OK 응답
        }else{  // 장소 생성에 실패한 경우
            throw new SQLException();
        }
    }

    @PutMapping("/{placeId}")    // 장소 수정 by id
    public ResponseEntity<ApiInfoResponse<Place>> updatePlaceById(@AuthenticationPrincipal CustomUserDetails user,
                                                                  @PathVariable("placeId") String placeId,
                                                                  @Valid @RequestBody Place place,
                                                                  BindingResult bindingResult) throws SQLException{
        if(bindingResult.hasErrors()){
            throw new InvalidInputException();
        }

        Place queriedPlace = placeService.findById(placeId);
        if(queriedPlace == null){  // 해당 id의 장소가 존재하지 않는 경우
            return ResponseEntity.notFound().build();   // 404 응답
        }else if(!queriedPlace.getWriterId().equals(user.getId())){   // 해당 장소를 생성한 사람이 아니라면
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 응답
        }else{  // 해당 id의 장소가 존재하는 경우
            place.setId(placeId);
            int updateCnt = placeService.updateById(place);
            if(updateCnt == 1){ // 장소 수정에 성공한 경우
                ApiInfoResponse<Place> apiResponse = new ApiInfoResponse<>();
                Place updatedPlace = placeService.findById(placeId);  // 수정된 place 조회
                apiResponse.setData(updatedPlace);
                return ResponseEntity.ok(apiResponse);  // 200 응답
            }else{  // 장소 수정에 실패한 경우
                throw new SQLException();
            }
        }
    }
}
