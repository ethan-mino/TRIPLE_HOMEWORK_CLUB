package com.triple.club.api.review.service;

import com.triple.club.api.file.service.FileService;
import com.triple.club.api.file.vo.FileVO;
import com.triple.club.api.review.dto.ReviewDetails;
import com.triple.club.api.review.mapper.ReviewImageMapper;
import com.triple.club.api.review.mapper.ReviewMapper;
import com.triple.club.api.review.vo.Review;
import com.triple.club.api.review.vo.ReviewImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReviewService {

    private final FileService fileService;
    private final ReviewMapper reviewMapper;
    private final ReviewImageMapper reviewImageMapper;

    public ReviewService(ReviewMapper reviewMapper,
                         FileService fileService,
                         ReviewImageMapper reviewImageMapper){
        this.reviewMapper = reviewMapper;
        this.fileService = fileService;
        this.reviewImageMapper = reviewImageMapper;
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
    public int createReview(Review review, List<MultipartFile> reviewImages){
        int createCnt = reviewMapper.save(review);  // 리뷰 정보 저장

        if(createCnt == 1){
            String ownerId = review.getWriterId();  // 리뷰 작성자 id
            List<FileVO> reviewFileList = fileService.saveFiles(reviewImages, ownerId); // 이미지 파일을 물리적으로 저장
            for(FileVO reviewFile : reviewFileList){    // 각 이미지 파일에 대해 리뷰 이미지 정보를 저장
                ReviewImage reviewImage = ReviewImage.builder()
                                .reviewId(review.getId())
                                .imageFileId(reviewFile.getId())
                                .build();
                reviewImageMapper.save(reviewImage);    // 리뷰 이미지 정보 저장
            }
            return createCnt;   // 리뷰 정보를 생성한 개수 반환
        }else{
            return 0;
        }
    }

    @Transactional(readOnly = false)
    public int updateById(Review review,
                                List<MultipartFile> reviewImages){

        int updateCnt = reviewMapper.updateById(review);  // 리뷰 정보 저장
        if(updateCnt == 1){
            String ownerId = review.getWriterId();  // 리뷰 작성자 id
            String reviewId = review.getId();   // 리뷰 아이디
            List<FileVO> reviewFileList = fileService.saveFiles(reviewImages, ownerId); // 이미지 파일을 물리적으로 저장

            reviewImageMapper.deleteByReviewId(reviewId);   // 해당 리뷰의 이미지 정보 제거
            for(FileVO reviewFile : reviewFileList){    // 각 이미지 파일에 대해 리뷰 이미지 정보를 저장
                ReviewImage reviewImage = ReviewImage.builder()
                        .reviewId(review.getId())
                        .imageFileId(reviewFile.getId())
                        .build();
                reviewImageMapper.save(reviewImage);    // 리뷰 이미지 정보 저장
            }
            return updateCnt;   // 리뷰 정보를 수정한 개수 반환
        }else{
            return 0;
        }
    }

    @Transactional(readOnly = false)
    public int deleteById(String id){
        return reviewMapper.deleteById(id);
    }
}
