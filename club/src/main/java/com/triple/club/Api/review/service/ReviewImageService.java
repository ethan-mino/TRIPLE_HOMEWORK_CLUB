package com.triple.club.Api.review.service;

import com.triple.club.Api.review.mapper.ReviewImageMapper;
import com.triple.club.Api.review.vo.ReviewImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewImageService {
    private final ReviewImageMapper reviewImageMapper;
    public ReviewImageService(ReviewImageMapper reviewImageMapper){
        this.reviewImageMapper = reviewImageMapper;
    }
    List<ReviewImage> findByReviewId(String reviewId){
        return reviewImageMapper.findByReviewId(reviewId);
    }

    int save(ReviewImage reviewImage){
        return reviewImageMapper.save(reviewImage);
    }
}
