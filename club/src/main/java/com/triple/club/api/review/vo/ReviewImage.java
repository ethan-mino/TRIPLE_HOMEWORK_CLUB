package com.triple.club.api.review.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewImage {
    private String id;
    private String imageFileId;
    private String reviewId;

    @Builder
    public ReviewImage(String id, String imageFileId, String reviewId){
        super();
        this.id = id;
        this.imageFileId = imageFileId;
        this.reviewId = reviewId;
    }
}
