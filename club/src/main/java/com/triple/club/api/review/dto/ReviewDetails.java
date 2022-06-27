package com.triple.club.api.review.dto;

import com.triple.club.api.review.entity.ReviewEntity;
import com.triple.club.api.review.entity.ReviewImageEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReviewDetails extends ReviewEntity {
    List<ReviewImageEntity> attachedPhotoIds;
}
