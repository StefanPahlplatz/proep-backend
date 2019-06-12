package com.bfwg.rest;

import com.bfwg.model.Review;
import com.bfwg.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController
@RequestMapping( value="/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public Review saveReview(Review review){
        return reviewService.save(review);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{reviewId}")
    public Review getReview(@PathVariable Long reviewId){
        return reviewService.get(reviewId);
    }
}
