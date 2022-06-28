package com.triple.club.api.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointVariance {
    private String userId;
    private String reviewId;
    private int variance;

    public PointVariance(String userId, String reviewId, int variance){
        this.userId = userId;
        this.reviewId = reviewId;
        this.variance = variance;
    }
}
