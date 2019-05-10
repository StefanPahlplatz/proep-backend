package com.bfwg.service.impl;

import com.bfwg.model.*;
import com.bfwg.repository.ReservationRepository;
import com.bfwg.service.AvailableService;
import com.bfwg.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ReservationServiceImplIntegrationTest {

    @InjectMocks
    private ReservationService reservationService = new ReservationServiceImpl();

    @Mock
    private AvailableService availableService;

    @MockBean
    private ReservationRepository reservationRepository;

    //test objects
    private User user1;

    private Vehicle vehicle;

    private Available available1;
    private Available available2;

    private Reservation reservation;

    private SimpleDateFormat format;

    @Before
    public void init() throws ParseException {

        MockitoAnnotations.initMocks(this);

        format = new SimpleDateFormat("yyyy-MM-dd");

        user1 = new User();
        user1.setUsername("testUser1");

        vehicle = new Vehicle("BMW", "X5","Luxury", 20000, "BD51SMR","red", 300.00,user1);

        available1 = new Available(format.parse("2019-04-09"),format.parse("2019-04-28"),vehicle);
        available2 = new Available(format.parse("2019-05-04"),format.parse("2019-05-14"),vehicle);

        Date start_date = format.parse("2019-04-09");
        Date end_date = format.parse("2019-04-12");

        reservation = new Reservation(start_date, end_date, 200.00, true, false, false,
                user1, vehicle);
    }

    @Test
    public void whenValidReservation_thenReservationShouldBeSavedAndReturned() {

        Mockito.when(availableService.findByStartdateBeforeAndEnddateAfterAndVehicle(reservation.getStartdate(),reservation.getEnddate(),vehicle))
                .thenReturn(available1);

        Mockito.when(availableService.save(available1)).thenReturn(available1);

        Mockito.when(reservationRepository.save(reservation))
                .thenReturn(reservation);

        Reservation saved = reservationService.save(reservation);

        assertThat(saved)
                .isEqualTo(reservation);
    }

    @Test
    public void whenInValidAvailable_thenReservationShouldNotBeSavedAndReturnNull() {

        Mockito.when(availableService.findByStartdateBeforeAndEnddateAfterAndVehicle(reservation.getStartdate(),reservation.getEnddate(),vehicle))
                .thenReturn(null);

        Reservation saved = reservationService.save(reservation);

        assertThat(saved)
                .isEqualTo(null);
    }
}
