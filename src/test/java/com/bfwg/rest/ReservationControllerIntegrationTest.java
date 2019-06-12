package com.bfwg.rest;

import com.bfwg.AbstractTest;
import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.security.auth.IAuthenticationFacade;
import com.bfwg.service.ReservationService;
import com.bfwg.service.UserService;
import com.bfwg.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebAppConfiguration
public class ReservationControllerIntegrationTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private UserService userService;

    @MockBean
    IAuthenticationFacade authenticationFacade;

    @Test
    public void givenReservations_whenFindAll_thenReturnJsonArray()
            throws Exception {

        Reservation reservation = new Reservation();
        reservation.setPrice(200.00);

        List<Reservation> allReservations = Arrays.asList(reservation);

        given(reservationService.findAll()).willReturn(allReservations);

        mvc.perform(get("/api/reservation/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(reservation.getPrice())));
    }

    @Test
    public void givenReservation_whenFindById_thenReturnJsonArray()
            throws Exception {

        Reservation reservation = new Reservation();
        reservation.setPrice(400.00);
        reservation.setId(new Long(123456));

        given(reservationService.findById(reservation.getId())).willReturn(reservation);

        mvc.perform(get("/api/reservation/"+reservation.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("price", is(reservation.getPrice())));
    }

    @Test
    public void givenReservation_whenFindByIdInvalidId_thenReturnNotFound()
            throws Exception {

        Reservation reservation = new Reservation();
        reservation.setPrice(400.00);
        reservation.setId(new Long(123456));

        given(reservationService.findById(reservation.getId())).willReturn(null);

        mvc.perform(get("/api/reservation/"+reservation.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void givenReservation_addReservation_thenReturnJson()
            throws Exception {

        Reservation reservation = new Reservation();
        reservation.setPrice(400.00);
        reservation.setId(new Long(123456));

        Vehicle vehicle = new Vehicle();
        vehicle.setId(new Long(123456));

        User user = new User();
        user.setId(new Long(123456));

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(reservation);

        given(userService.findById(new Long(123456))).willReturn(user);

        given(vehicleService.findById(new Long(123456))).willReturn(vehicle);

        given(reservationService.save(any(Reservation.class))).willReturn(reservation);

        given(reservationService.save(any(Reservation.class))).willReturn(reservation);

        mvc.perform(post("/api/reservation/user/"+user.getId()+"/vehicle/"+vehicle.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("price", is(reservation.getPrice())));
    }

    @Test
    public void givenReservationId_deleteReservation_thenReturnNoContent() throws Exception{

        Reservation reservation = new Reservation();
        reservation.setPrice(400.00);
        reservation.setId(new Long(123456));

        User user = new User();
        user.setUsername("user");
        user.setId(123l);

        reservation.setUser(user);

        given(reservationService.findById(reservation.getId())).willReturn(reservation);

        given(authenticationFacade.getPrincipal()).willReturn(user);

        verify(reservationService,times(0)).delete(reservation);

        mvc.perform(delete("/api/reservation/{id}",reservation.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
