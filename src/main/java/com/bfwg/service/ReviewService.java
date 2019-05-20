package com.bfwg.service;

import com.bfwg.model.Review;

public interface ReviewService {

    Review save(Review reviewrequest);

    Review get(long id);

    Review find(long id);
}
