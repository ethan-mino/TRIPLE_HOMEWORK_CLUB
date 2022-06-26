package com.triple.club.Api.review.mapper;

import com.triple.club.Api.review.dto.ReviewDetails;
import com.triple.club.Api.review.vo.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    ReviewDetails findById(String id);
    List<ReviewDetails> findByWriterId(String writerId);
    List<ReviewDetails> findByPlaceId(String placeId);
    int save(Review review);
    int updateById(Review review);
    int deleteById(String id);
}
