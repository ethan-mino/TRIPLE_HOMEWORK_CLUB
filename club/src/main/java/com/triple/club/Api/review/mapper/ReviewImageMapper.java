package com.triple.club.Api.review.mapper;

import com.triple.club.Api.review.vo.ReviewImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewImageMapper {
    List<ReviewImage> findByReviewId(String reviewId);
    int save(ReviewImage reviewImage);
    int deleteByReviewId(String reviewId);
}
