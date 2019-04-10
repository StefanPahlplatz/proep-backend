package com.bfwg.service.impl;

import com.bfwg.model.Review;
import com.bfwg.repository.ReviewRepository;
import com.bfwg.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review save(Review reviewrequest) {
        return this.reviewRepository.save(reviewrequest);
    }
}
