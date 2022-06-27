package com.triple.club.api.review.dto;

import com.triple.club.api.review.vo.Review;
import com.triple.club.api.review.vo.ReviewImage;
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