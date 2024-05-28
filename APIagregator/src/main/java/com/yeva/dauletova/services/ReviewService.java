package com.yeva.dauletova.services;

import com.yeva.dauletova.models.Like;
import com.yeva.dauletova.models.Review;
import com.yeva.dauletova.models.User;
import com.yeva.dauletova.repositories.LikeRepository;
import com.yeva.dauletova.repositories.ReviewRepository;
import com.yeva.dauletova.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    private LikeRepository likeRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    @Autowired
    public ReviewService(LikeRepository likeRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }



    public void create(Review review){
        reviewRepository.save(review);
    }
    public Page<Review> getReviews(int page, int pageSize){
        Pageable curPage= PageRequest.of(page, pageSize);
        Page<Review> reviews = reviewRepository.findAll(curPage);
        return reviews;

    }
    public void likeReview(int userId,int reviewId){
    Optional<User> user = userRepository.findById((long)userId);
    Optional<Review> review = reviewRepository.findById((long)reviewId);
    if(user.isPresent()&& review.isPresent()){
        Like like = new Like();
        like.setUser(user.get());
        like.setReview(review.get());
        likeRepository.save(like);
    }
    }
    public void unlikeReview(int userId,int reviewId){
        Optional<User> user = userRepository.findById((long)userId);
        Optional<Review> review = reviewRepository.findById((long)reviewId);
        if(user.isPresent()&& review.isPresent()){
            Like like = new Like();
            like.setUser(user.get());
            like.setReview(review.get());
            likeRepository.delete(like);
        }
    }
    public boolean checkLike(Long userId, Long reviewId){
        return likeRepository.existsByUserIdAndReviewId(userId, reviewId);
    }
    public boolean isReviewLikeByUser(Review review, User user){
        return likeRepository.existsByReviewAndUser(review, user);
    }
    public Long countLikesForReview(Review review){
    return likeRepository.countByReview(review);
    }
}

