package com.yeva.dauletova.repositories;

import com.yeva.dauletova.models.Like;
import com.yeva.dauletova.models.Review;
import com.yeva.dauletova.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Like l WHERE l.user.id = :userId AND l.review.id = :reviewId")
    boolean existsByUserIdAndReviewId(@Param("userId")Long userId, @Param("reviewId")Long reviewId);
    Long countByReview(Review review);
    boolean existsByReviewAndUser(Review review, User user);
}
