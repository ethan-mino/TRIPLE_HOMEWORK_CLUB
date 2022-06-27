package com.triple.club.api.review.dto;

import com.triple.club.api.review.EarnPointAction;
import com.triple.club.api.review.vo.ReviewImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EarnPointRequest {
    private String type;
    private EarnPointAction action;
    private String reviewId;
    private String content;
    private List<ReviewImage> attachedPhotoIds;
    private String userId;
    private String placeId;
}
