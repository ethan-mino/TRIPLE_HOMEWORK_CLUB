package com.triple.club.api.review.mapper;

import com.triple.club.api.review.entity.ReviewImageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewImageMapper {
    List<ReviewImageEntity> findByReviewId(String reviewId);
    int save(ReviewImageEntity reviewImage);
    int deleteByReviewId(String reviewId);
}
