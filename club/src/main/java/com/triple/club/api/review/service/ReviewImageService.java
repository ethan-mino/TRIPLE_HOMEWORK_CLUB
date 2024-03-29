package com.triple.club.api.review.service;

import com.triple.club.api.review.mapper.ReviewImageMapper;
import com.triple.club.api.review.entity.ReviewImageEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewImageService {
    private final ReviewImageMapper reviewImageMapper;
    public ReviewImageService(ReviewImageMapper reviewImageMapper){
        this.reviewImageMapper = reviewImageMapper;
    }
    List<ReviewImageEntity> findByReviewId(String reviewId){
        return reviewImageMapper.findByReviewId(reviewId);
    }

    @Transactional(readOnly = false)
    int save(ReviewImageEntity reviewImage){
        return reviewImageMapper.save(reviewImage);
    }
}
