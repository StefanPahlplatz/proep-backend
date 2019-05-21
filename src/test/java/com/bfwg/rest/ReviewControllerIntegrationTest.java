package com.bfwg.rest;


import com.bfwg.AbstractTest;
import com.bfwg.model.Reservation;
import com.bfwg.model.Review;
import com.bfwg.service.ReservationService;
import com.bfwg.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebAppConfiguration
public class ReviewControllerIntegrationTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void givenReview_addReview_thenReturnJson()
            throws Exception {

        Review review = new Review();
        review.setComment("this is a review comment");
        review.setRating(4.50);
        review.setType("user");

        Reservation reservation = new Reservation();
        reservation.setId(new Long(123456));

        review.setReservation(reservation);
        ObjectMapper mapper = new ObjectMapper();

        given(reviewService.save(any(Review.class))).willReturn(review);

        mvc.perform(put("/api/reviews/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(review.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("rating", is(review.getRating())));
    }

    @Test
    public void getReview_ReturnsNotFound_WhenReviewDoesNotExist() throws Exception {

        // Arrange
        Review review = new Review();
        review.setComment("This car is really fast");
        review.setRating(4.0);
        review.setType("user");

        Reservation reservation = new Reservation();
        reservation.setId((long)12345);

        review.setReservation(reservation);

        given(reservationService.findById(reservation.getId()))
                .willReturn(null);

        ObjectMapper mapper = new ObjectMapper();

        // Assert
        mvc.perform(get("/api/review/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation.getId())))
                .andExpect(status().isNotFound());
    }
}
