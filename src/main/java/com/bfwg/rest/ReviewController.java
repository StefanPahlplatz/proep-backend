package com.bfwg.rest;

import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.Reservation;
import com.bfwg.model.Review;
import com.bfwg.service.ReservationService;
import com.bfwg.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController
@RequestMapping( value="/api/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = POST, value = "/")
    public ResponseEntity<?> addReview(@RequestBody Review reviewrequest){

        Reservation reservation = this.reservationService.findById(reviewrequest.getReservation().getId());

        if(reservation == null){
            return new ResponseEntity<>("there was no reservation with the id "+reviewrequest.getId(),HttpStatus.NOT_FOUND);
        }

        reviewrequest.setReservation(reservation);

        Review review = this.reviewService.save(reviewrequest);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
}
