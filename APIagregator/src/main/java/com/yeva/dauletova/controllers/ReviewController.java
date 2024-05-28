package com.yeva.dauletova.controllers;

import com.yeva.dauletova.DTO.ReviewDTO;
import com.yeva.dauletova.models.MyUserDetails;
import com.yeva.dauletova.models.Review;
import com.yeva.dauletova.models.User;
import com.yeva.dauletova.repositories.ReviewRepository;
import com.yeva.dauletova.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
private ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String reviewsPage(Integer page, Model model, Authentication authentication){
        int pageSize = 1;
        if(page==null){
            page=0;
        }
        Page<Review> reviews= reviewService.getReviews(page, pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalItems", reviews.getTotalElements());
        model.addAttribute("totalPages", reviews.getTotalPages());
        List<ReviewDTO> reviewsDTO = new ArrayList<>();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        for(Review review:reviews){
            Long likeCount = reviewService.countLikesForReview(review);
            boolean isLiked = userDetails!=null&&reviewService.isReviewLikeByUser(review, userDetails.getUser());
            reviewsDTO.add(new ReviewDTO(review, likeCount, isLiked));
        }
        model.addAttribute("reviews", reviewsDTO);
        if(userDetails!=null){
            model.addAttribute("user", userDetails.getUser().getId());

        }
        else {
            model.addAttribute("user", 0);
        }

        return "reviews";
    }

    @PostMapping
    public String createReview(@RequestParam("review") String review, Authentication authentication){
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        Review review1 = new Review(review, userDetails.getUser());
        reviewService.create(review1);
        return "redirect:/reviews";

    }
    @GetMapping("/like/{id}")
    public String likeReview(@PathVariable("id") int id, Authentication authentication){
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        reviewService.likeReview(userDetails.getUser().getId(),id);
        return "redirect:/reviews";
    }

    @GetMapping("/unlike/{id}")
    public String unlikeReview(@PathVariable("id") int id, Authentication authentication){
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        reviewService.unlikeReview(userDetails.getUser().getId(),id);
        return "redirect:/reviews";
    }
   private Map<Review, Boolean> getLiked(Long userId, List<Review> reviews){
    Map<Review, Boolean> likes = new HashMap<>();
    for (Review review:reviews){
        likes.put(review, reviewService.checkLike(userId, review.getId()));
    }
    return likes;
   }

}
