package com.yeva.dauletova.controllers;

import com.yeva.dauletova.models.MyUserDetails;
import com.yeva.dauletova.models.Review;
import com.yeva.dauletova.models.User;
import com.yeva.dauletova.repositories.ReviewRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewRepository reviewRepository;
    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public String reviewsPage(Integer page, Model model){
        int pageSize = 20;
        if(page==null){
            page=0;
        }
        Pageable curPage= PageRequest.of(page, pageSize);
        Page<Review> reviews = reviewRepository.findAll(curPage);
        model.addAttribute("reviews", reviews);
        return "reviews";
    }
    @PostMapping
    public String createReview(@RequestParam("review")String review, Authentication authentication){
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        Review review1 = new Review(review, userDetails.getUser());
        reviewRepository.save(review1);
        return "reviews";

    }


}
