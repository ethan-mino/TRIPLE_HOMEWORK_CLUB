package com.triple.club.api.review.service;

import com.triple.club.api.file.entity.FileEntity;
import com.triple.club.api.file.service.FileService;
import com.triple.club.api.review.dto.PointVariance;
import com.triple.club.api.review.dto.ReviewDetails;
import com.triple.club.api.review.entity.PointLog;
import com.triple.club.api.review.entity.ReviewEntity;
import com.triple.club.api.review.entity.ReviewImageEntity;
import com.triple.club.api.review.mapper.ReviewImageMapper;
import com.triple.club.api.review.mapper.ReviewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
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

    private int calReviewPoint(ReviewDetails review){
        String reviewId = review.getId();    // 대상 리뷰의 id
        String placeId = review.getPlaceId();

        int pointVariance = 0;  // 변동된 포인트
        ReviewEntity firstReview = reviewMapper.findFirstReviewByPlaceId(placeId); // 해당 장소의 첫 리뷰 확인
        if(review.getContent() != null && review.getContent().trim().length() > 0) {    // 1자 이상 텍스트를 작성한 경우 (양쪽 공백 제외)
            pointVariance++;
        }
        if(review.getAttachedPhotoIds().size() > 0){    // 1장 이상 사진 첨부한 경우
            pointVariance++;
        }
        if(firstReview != null && reviewId.equals(firstReview.getId())){    // 리뷰가 해당 장소의 첫 리뷰인 경우
            pointVariance++;
        }

        return pointVariance;
    }

    @Transactional(readOnly = true)
    public Integer findPointByUserId(String userId){
        Integer point = reviewMapper.findPointByUserId(userId);
        if(point == null) point = 0;
        return point;
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
            int newPoint = calReviewPoint(reviewDetails);
            PointVariance pointVariance = new PointVariance(ownerId, reviewId, newPoint);
            updatePoint(pointVariance); // 포인트 변경
        }

        return createCnt;   // 리뷰 정보를 생성한 개수 반환
    }

    @Transactional(readOnly = false)
    public int updateById(ReviewEntity review,
                          List<MultipartFile> reviewImages) throws TransactionException {

        String reviewId = review.getId();   // 리뷰 아이디
        ReviewDetails BeforeUpdateReview = findById(reviewId);  // 업데이트 전 리뷰

        int updateCnt = reviewMapper.updateById(review);  // 리뷰 정보 저장
        if(updateCnt == 1){
            String ownerId = review.getWriterId();  // 리뷰 작성자 id
            List<FileEntity> reviewFileList = fileService.saveFiles(reviewImages, ownerId); // 이미지 파일을 물리적으로 저장

            reviewImageMapper.deleteByReviewId(reviewId);   // 해당 리뷰의 이미지 정보 제거
            for(FileEntity reviewFile : reviewFileList){    // 각 이미지 파일에 대해 리뷰 이미지 정보를 저장
                ReviewImageEntity reviewImage = ReviewImageEntity.builder()
                        .reviewId(review.getId())
                        .imageFileId(reviewFile.getId())
                        .build();
                reviewImageMapper.save(reviewImage);    // 리뷰 이미지 정보 저장
            }

            ReviewDetails updatedReview = findById(reviewId);
            int prePoint = calReviewPoint(BeforeUpdateReview);
            int postPoint = calReviewPoint(updatedReview);
            int variance = postPoint - prePoint;    // 포인트 증감

            PointVariance pointVariance = new PointVariance(ownerId, reviewId, variance);
            updatePoint(pointVariance);
        }
        return updateCnt;   // 리뷰 정보를 수정한 개수 반환
    }

    @Transactional(readOnly = false)
    public int deleteById(String id){
        ReviewDetails review = findById(id);
        String writerId = review.getWriterId();
        String placeId = review.getPlaceId();

        int prePoint = calReviewPoint(review);  // 지워진 리뷰의 포인트
        int variance = prePoint * -1;   // 포인트 증감

        int deleteCnt = reviewMapper.deleteById(id);
        PointVariance pointVar = new PointVariance(writerId, id, variance);
        updatePoint(pointVar);  // 해당 리뷰의 포인트 변경

        ReviewDetails firstReview = reviewMapper.findFirstReviewByPlaceId(placeId); // 리뷰를 지운 후, 해당 장소의 첫번째 리뷰
        if(firstReview != null){    // 리뷰를 지우고 난 후, 해당 장소에 작성된 리뷰가 존재한다면,
            int firstReviewPoint = calReviewPoint(firstReview);

            PointVariance firstReviewPointVar = new PointVariance(firstReview.getWriterId(),
                    firstReview.getId(), firstReviewPoint);
            updatePoint(firstReviewPointVar); // 포인트 변경
        }

        return deleteCnt;
    }

    @Transactional(readOnly = false)
    public int updatePoint(PointVariance pointVar){ // 리뷰와 유저의 포인트를 변경, 증감 이력 저장
        int variance = pointVar.getVariance();
        String ownerId = pointVar.getUserId();
        String reviewId = pointVar.getReviewId();

        int updateCnt = reviewMapper.updateReviewPointByOwnerId(ownerId, variance); // 유저의 누적 포인트 변경
        reviewMapper.updatePointById(reviewId, variance);   // 리뷰의 포인트 변경
        PointLog pointLog = PointLog.builder()
                .ownerId(ownerId)
                .variance(variance)
                .build();

        reviewMapper.saveLog(pointLog); // 포인트 증감 이력 저장
        return updateCnt;
    }
}
