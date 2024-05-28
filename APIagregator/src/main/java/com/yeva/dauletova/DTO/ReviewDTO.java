package com.yeva.dauletova.DTO;

import com.yeva.dauletova.models.Review;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewDTO {
    public Review review;
    public Long likeCount;
    public boolean isLiked;

}
