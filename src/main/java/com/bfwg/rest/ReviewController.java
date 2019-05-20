package com.bfwg.rest;

import com.bfwg.model.Review;
import com.bfwg.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value="/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(method = RequestMethod.PUT, value = "/save")
    public Review saveReview(Review review){
        return reviewService.save(review);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Review getReview(long id){
        return reviewService.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find")
    public Review findReview(long id){
        return reviewService.find(id);
    }


}
