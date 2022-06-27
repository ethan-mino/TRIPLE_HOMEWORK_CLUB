package com.triple.club.api.review.mapper;

import com.triple.club.api.review.dto.ReviewDetails;
import com.triple.club.api.review.entity.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    ReviewDetails findById(String id);
    List<ReviewDetails> findByWriterId(String writerId);
    List<ReviewDetails> findByPlaceId(String placeId);
    int save(ReviewEntity review);
    int updateById(ReviewEntity review);
    int deleteById(String id);
}
