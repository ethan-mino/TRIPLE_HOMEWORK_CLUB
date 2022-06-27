package com.triple.club.api.review.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewImageEntity {
    private String id;
    private String imageFileId;
    private String reviewId;

    @Builder
    public ReviewImageEntity(String id, String imageFileId, String reviewId){
        super();
        this.id = id;
        this.imageFileId = imageFileId;
        this.reviewId = reviewId;
    }
}
