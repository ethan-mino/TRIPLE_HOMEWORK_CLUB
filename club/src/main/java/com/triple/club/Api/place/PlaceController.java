package com.triple.club.Api.place;

import com.triple.club.Api.place.vo.Place;
import com.triple.club.Api.user.dto.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {
    // 장소 생성
    // 장소 조회 by id
    // 모든 장소 조회
    // 장소 수정 by id
    // 장소 삭제 by id
    // TODO * 리뷰가 작성된 장소 DB가 사라질 때도 포인트를 줄이기

    @GetMapping("")
    public ResponseEntity<List<Place>> getAllPlace(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        System.out.println("asd");
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Place>> getPlaceById(@PathVariable String id){
        return null;
    }

}
