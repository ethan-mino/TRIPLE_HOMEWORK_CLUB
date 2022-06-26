package com.triple.club.Api.review.dto;

import com.triple.club.Api.review.vo.Review;
import com.triple.club.Api.review.vo.ReviewImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReviewDetails extends Review {
    List<ReviewImage> attachedPhotoIds;
}
