package com.triple.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    ! TODO Advice 이용하여 Controller Error 처리
    ! TODO 리뷰가 작성된 장소 DB가 사라질 때도 포인트를 줄이기
    ! TODO 성능 향상을 위한 인덱스 처리
    ! TODO Controller try catch문으로 에러 처리 (DB ERROR, INTERNAL SERVER ERROR)
    ! TODO Controller 메서드의 응답을 HTTP Response status code 명세에 맞게 수정
    ! TODO ApiResult의 code를 HTTP Response Status code Class에 맞게 수정
    * TODO Sweager 사용하여 API 명세
 */

@SpringBootApplication
public class ClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubApplication.class, args);
    }

}
