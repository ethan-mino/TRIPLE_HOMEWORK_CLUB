package com.triple.club.api.review.controller;

import com.triple.club.api.exception.InvalidInputException;
import com.triple.club.api.review.dto.ReviewDetails;
import com.triple.club.api.review.service.ReviewService;
import com.triple.club.api.review.entity.ReviewEntity;
import com.triple.club.api.user.dto.CustomUserDetails;
import com.triple.club.api.util.ApiInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("points/my-point")  // 유저의 리뷰 포인트 조회
    public ResponseEntity<ApiInfoResponse<Integer>> getUserPoints(@AuthenticationPrincipal CustomUserDetails user){
        ApiInfoResponse<Integer> apiResponse = new ApiInfoResponse<>();
        int point = reviewService.findPointByUserId(user.getId());
        apiResponse.setData(point);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{placeId}") // 특정 장소의 리뷰 조회
    public ResponseEntity<ApiInfoResponse<List<ReviewDetails>>> getReviewByPlaceId(@PathVariable("placeId") String placeId){
        ApiInfoResponse<List<ReviewDetails>> apiResponse = new ApiInfoResponse<>();
        List<ReviewDetails>  reviewList = reviewService.findByPlaceId(placeId);

        if(reviewList.size() == 0){  // 해당 장소에 등록된 리뷰가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }else{  // 해당 id의 장소가 존재하는 경우
            apiResponse.setData(reviewList);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @GetMapping("/my-review") // 사용자의 리뷰 전체 조회
    public ResponseEntity<ApiInfoResponse<List<ReviewDetails>>> getReviewByWriterId(@AuthenticationPrincipal CustomUserDetails user){
        String writer = user.getId();
        ApiInfoResponse<List<ReviewDetails>> apiResponse = new ApiInfoResponse<>();
        List<ReviewDetails>  reviewList = reviewService.findByWriterId(writer);

        if(reviewList.size() == 0){  // 유저가 작성한 리뷰가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }else{  // 유저가 작성한 리뷰가 존재하는 경우
            apiResponse.setData(reviewList);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @PostMapping("/{placeId}")   // 리뷰 생성
    public ResponseEntity<ApiInfoResponse<ReviewDetails>> createReview(@AuthenticationPrincipal CustomUserDetails user,
                                                                       List<MultipartFile> reviewImages,
                                                                       @PathVariable("placeId") String placeId,
                                                                       @Valid ReviewEntity review,
                                                                       BindingResult bindingResult) throws SQLException{
        if(bindingResult.hasErrors()){  // 리뷰 정보 Validation
            throw new InvalidInputException();
        }

        ApiInfoResponse<ReviewDetails> apiResponse = new ApiInfoResponse<>();
        String writerId = user.getId(); // 리뷰 작성자의 id
        review.setWriterId(writerId);
        review.setPlaceId(placeId);

        int createCnt = reviewService.createReview(review, reviewImages);   // 리뷰 생성
        if(createCnt == 1){
            ReviewDetails createdReview = reviewService.findById(review.getId());  // 생성된 리뷰 조회
            apiResponse.setData(createdReview);
            return ResponseEntity.ok(apiResponse);  // 200 OK 응답
        }else{  // 리뷰 생성에 실패한 경우
            throw new SQLException();
        }
    }

    @PutMapping("/{reviewId}")   // 특정 리뷰 수정
    public ResponseEntity<ApiInfoResponse<ReviewDetails>> updateReview(@AuthenticationPrincipal CustomUserDetails user,
                                                                       List<MultipartFile> reviewImages,
                                                                       @PathVariable("reviewId") String reviewId,
                                                                       @Valid ReviewEntity review,
                                                                       BindingResult bindingResult) throws SQLException{
        if(bindingResult.hasErrors()){  // 리뷰 정보 Validation
            throw new InvalidInputException();
        }

        ReviewDetails queriedReview = reviewService.findById(reviewId);
        if(queriedReview == null){  // 해당 id의 리뷰가 존재하지 않는 경우
            return ResponseEntity.notFound().build();   // 404 응답
        }else if(!queriedReview.getWriterId().equals(user.getId())){   // 해당 리뷰를 작성한 사람이 아니라면
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 응답
        }else{  // 해당 id의 리뷰가 존재하는 경우
            String writerId = user.getId(); // 리뷰 작성자의 id

            review.setId(reviewId);
            review.setWriterId(writerId);
            int updateCnt = reviewService.updateById(review, reviewImages);
            if(updateCnt == 1){ // 리뷰 정보 수정에 성공한 경우
                ApiInfoResponse<ReviewDetails> apiResponse = new ApiInfoResponse<>();
                ReviewDetails updatedReview = reviewService.findById(reviewId);  // 수정된 리뷰 조회
                apiResponse.setData(updatedReview);
                return ResponseEntity.ok(apiResponse);  // 200 응답
            }else{  // 장소 수정에 실패한 경우
                throw new SQLException();
            }
        }
    }

    @DeleteMapping("/{reviewId}")   // 특정 리뷰 삭제
    public ResponseEntity deleteReview(@AuthenticationPrincipal CustomUserDetails user,
                               @PathVariable("reviewId") String reviewId) throws SQLException {

        ReviewDetails queriedReview = reviewService.findById(reviewId);
        if(queriedReview == null){  // 해당 id의 리뷰가 존재하지 않는 경우
            return ResponseEntity.notFound().build();   // 404 응답
        }else if(!queriedReview.getWriterId().equals(user.getId())){   // 해당 리뷰를 작성한 유저가 아니라면
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 응답
        }else{  // 해당 id의 리뷰가 존재하는 경우
            int deleteCnt = reviewService.deleteById(reviewId);
            if(deleteCnt == 1){ // 장소 수정에 성공한 경우
                return ResponseEntity.status(HttpStatus.OK).build(); // 200 응답
            }else{  // 리뷰 수정에 실패한 경우
                throw new SQLException();
            }
        }
    }
}
