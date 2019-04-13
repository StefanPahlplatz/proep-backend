package com.bfwg.repository;

import com.bfwg.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    //objects for testing
    //user objects
    User user1;
    User user2;

    //vehicle objects
    Vehicle vehicle1;

    //reservation objects
    Reservation reservation1;
    Reservation reservation2;

    //date object
    SimpleDateFormat format;


    @Before
    public void initialize() throws ParseException {

        //given
        format = new SimpleDateFormat("yyyy-MM-dd");

        user1 = new User();
        user1.setUsername("testUser1");
        user2 = new User();
        user2.setUsername("testUser2");

        vehicle1 = new Vehicle("BMW", "X5","Luxury", 20000, "BD51SMR","red", 300.00,user1);

        reservation1 = new Reservation(format.parse("2019-05-09"),format.parse("2019-05-15"), 220.00, true, false, false,
                user1, vehicle1, null, null);

        reservation2 = new Reservation(format.parse("2019-06-09"),format.parse("2019-06-19"), 220.00, true, false, false,
                user2, vehicle1, null, null);

        entityManager.persist(user1);
        entityManager.persist(user2);

        entityManager.persist(vehicle1);

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);

        entityManager.flush();
    }

    @Test
    public void whenFindByUserId_thenReturnReservationList(){

        // when
        List<Reservation> found = reservationRepository.findByUser_Id(user1.getId());

        // then
        assertThat(found.get(0).getVehicle()).
                isEqualTo(reservation1.getVehicle());

        assertThat(found.size()).isEqualTo(1);
    }
}
