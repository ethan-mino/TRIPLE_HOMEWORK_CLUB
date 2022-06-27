package com.triple.club.api.review.service;

import com.triple.club.api.exception.FailToEarnPointException;
import com.triple.club.api.exception.TransactionFailureException;
import com.triple.club.api.file.service.FileService;
import com.triple.club.api.file.entity.FileEntity;
import com.triple.club.api.review.EarnPointAction;
import com.triple.club.api.review.dto.EarnPointRequest;
import com.triple.club.api.review.dto.ReviewDetails;
import com.triple.club.api.review.mapper.ReviewImageMapper;
import com.triple.club.api.review.mapper.ReviewMapper;
import com.triple.club.api.review.entity.ReviewEntity;
import com.triple.club.api.review.entity.ReviewImageEntity;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReviewService {
    private final FileService fileService;
    private final ReviewMapper reviewMapper;
    private final ReviewImageMapper reviewImageMapper;
    private final String POINT_EARN_TYPE = "REVIEW";
    private final String POINT_EARN_URL = "http://localhost:3030/events";

    public ReviewService(ReviewMapper reviewMapper,
                         FileService fileService,
                         ReviewImageMapper reviewImageMapper){
        this.reviewMapper = reviewMapper;
        this.fileService = fileService;
        this.reviewImageMapper = reviewImageMapper;
    }

    private void pointEarn(ReviewDetails review, HttpMethod method, EarnPointAction action){
        ModelMapper modelMapper = new ModelMapper();
        EarnPointRequest pointRequest = modelMapper.map(review, EarnPointRequest.class);
        pointRequest.setUserId(review.getWriterId());
        pointRequest.setAction(action);
        pointRequest.setType(POINT_EARN_TYPE);

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(pointRequest, headers);

        try{
            ResponseEntity<String> pointResponse = rt.exchange( // 포인트 적립 API에 request
                    POINT_EARN_URL, method,
                    entity, String.class
            );

            int statusCode = pointResponse.getStatusCodeValue();

            if(statusCode != HttpStatus.OK.value()  // 포인트 적립에 실패한 경우
                    && statusCode != HttpStatus.CREATED.value()
                    && statusCode == HttpStatus.NO_CONTENT.value()){
                throw new FailToEarnPointException();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new FailToEarnPointException();

        }
    }

    @Transactional(readOnly = true)
    public ReviewDetails findById(String id){
        return reviewMapper.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ReviewDetails> findByWriterId(String writerId){
        return reviewMapper.findByWriterId(writerId);
    }

    @Transactional(readOnly = true)
    public List<ReviewDetails> findByPlaceId(String placeId){
        return reviewMapper.findByPlaceId(placeId);
    }

    @Transactional(readOnly = false)
    public int createReview(ReviewEntity review, List<MultipartFile> reviewImages){
        int createCnt = reviewMapper.save(review);  // 리뷰 정보 저장

        if(createCnt == 1){
            String ownerId = review.getWriterId();  // 리뷰 작성자 id
            String reviewId = review.getId();

            List<FileEntity> reviewFileList = fileService.saveFiles(reviewImages, ownerId); // 이미지 파일을 물리적으로 저장
            for(FileEntity reviewFile : reviewFileList){    // 각 이미지 파일에 대해 리뷰 이미지 정보를 저장
                ReviewImageEntity reviewImage = ReviewImageEntity.builder()
                                .reviewId(review.getId())
                                .imageFileId(reviewFile.getId())
                                .build();
                reviewImageMapper.save(reviewImage);    // 리뷰 이미지 정보 저장
            }
            ReviewDetails reviewDetails = findById(reviewId);
            try{
                pointEarn(reviewDetails, HttpMethod.POST, EarnPointAction.ADD); // 포인트 적립
            }catch (FailToEarnPointException ex){
                ex.printStackTrace();
                throw new TransactionFailureException(null);   // 포인트 적립에 실패한 경우 throw SQLException
            }
        }

        return createCnt;   // 리뷰 정보를 생성한 개수 반환
    }

    @Transactional(readOnly = false)
    public int updateById(ReviewEntity review,
                          List<MultipartFile> reviewImages) throws TransactionException {

        int updateCnt = reviewMapper.updateById(review);  // 리뷰 정보 저장
        if(updateCnt == 1){
            String ownerId = review.getWriterId();  // 리뷰 작성자 id
            String reviewId = review.getId();   // 리뷰 아이디
            List<FileEntity> reviewFileList = fileService.saveFiles(reviewImages, ownerId); // 이미지 파일을 물리적으로 저장

            reviewImageMapper.deleteByReviewId(reviewId);   // 해당 리뷰의 이미지 정보 제거
            for(FileEntity reviewFile : reviewFileList){    // 각 이미지 파일에 대해 리뷰 이미지 정보를 저장
                ReviewImageEntity reviewImage = ReviewImageEntity.builder()
                        .reviewId(review.getId())
                        .imageFileId(reviewFile.getId())
                        .build();
                reviewImageMapper.save(reviewImage);    // 리뷰 이미지 정보 저장
            }

            ReviewDetails reviewDetails = findById(reviewId);
            try{
                pointEarn(reviewDetails, HttpMethod.POST, EarnPointAction.MOD); // 포인트 적립
            }catch (FailToEarnPointException ex){
                throw new TransactionFailureException(null);   // 포인트 적립에 실패한 경우 throw SQLException
            }
        }
        return updateCnt;   // 리뷰 정보를 수정한 개수 반환
    }

    @Transactional(readOnly = false)
    public int deleteById(String id){
        ReviewDetails reviewDetails = findById(id);

        int deleteCnt = reviewMapper.deleteById(id);

        try{
            pointEarn(reviewDetails, HttpMethod.POST, EarnPointAction.DELETE); // 포인트 적립
        }catch (FailToEarnPointException ex){
            throw new TransactionFailureException(null);   // 포인트 적립에 실패한 경우 throw SQLException
        }

        return deleteCnt;
    }
}
