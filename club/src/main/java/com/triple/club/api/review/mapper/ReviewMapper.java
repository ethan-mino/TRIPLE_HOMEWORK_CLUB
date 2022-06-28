package com.triple.club.api.review.mapper;

import com.triple.club.api.review.dto.ReviewDetails;
import com.triple.club.api.review.entity.PointLog;
import com.triple.club.api.review.entity.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<ReviewDetails> findByWriterId(String writerId);
    List<ReviewDetails> findByPlaceId(String placeId);
    ReviewDetails findById(String id);
    ReviewDetails findFirstReviewByPlaceId(String placeId);
    Integer findPointByUserId(String userId);
    int save(ReviewEntity review);

    int updatePointById(String id, int variance);
    int updateById(ReviewEntity review);
    int updateReviewPointByOwnerId(String ownerId, int variance);
    int deleteById(String id);

    int saveLog(PointLog pointLog);
}
