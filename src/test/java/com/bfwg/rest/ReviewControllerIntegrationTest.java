package com.bfwg.rest;


import com.bfwg.AbstractTest;
import com.bfwg.model.Reservation;
import com.bfwg.model.Review;
import com.bfwg.service.ReservationService;
import com.bfwg.service.ReviewService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        String requestJson = mapper.writeValueAsString(review);

        given(reservationService.findById(review.getReservation().getId())).willReturn(reservation);

        given(reviewService.save(any(Review.class))).willReturn(review);

        //given(reservationService.update(any(Reservation.class))).willReturn(reservation);

        mvc.perform(post("/api/review/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("rating", is(review.getRating())));
    }
}
