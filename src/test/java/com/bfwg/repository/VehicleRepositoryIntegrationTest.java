package com.bfwg.repository;

import com.bfwg.model.Available;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehicleRepository vehicleRepository;

    //objects for testing
    //user objects
    User user1;
    User user2;

    //vehicle objects
    Vehicle vehicle1;
    Vehicle vehicle2;
    Vehicle vehicle3;

    //vehicle list
    List<Vehicle> vehicles = new ArrayList<>();

    //available objects
    Available available1;
    Available available2;
    Available available3;

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
        vehicle2 = new Vehicle("Mercedes", "Sl","Luxury", 10000, "BD51SMS","blue", 100.00,user2);
        vehicle3 = new Vehicle("Lexus", "t3","Luxury", 40000, "BD51SMX","green", 500.00,user2);

        vehicle1.setLatitude(42.29);
        vehicle1.setLongitude(-71.1);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);

        available1 = new Available(format.parse("2019-04-09"),format.parse("2019-04-28"),vehicle1);
        available2 = new Available(format.parse("2019-04-19"),format.parse("2019-05-14"),vehicle2);
        available3 = new Available(format.parse("2019-05-19"),format.parse("2019-06-14"),vehicle3);

        entityManager.persist(user1);
        entityManager.persist(user2);

        entityManager.persist(available1);
        entityManager.persist(available2);
        entityManager.persist(available3);

        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.persist(vehicle3);

        entityManager.flush();
    }

    @Test
    public void whenFindByUser_thenReturnVehicleList(){

        // when
        List<Vehicle> found = vehicleRepository.findByUser(user1);

        // then
        assertThat(found.get(0).getRegistration()).
                isEqualTo(vehicle1.getRegistration());

    }

    @Test
    public void whenFindByPriceBetween_thenReturnVehicleList(){

        // when
        List<Vehicle> found = vehicleRepository.findByPriceBetween(200.00,400.00);

        // then
        assertThat(found.get(0).getRegistration()).
                isEqualTo(vehicle1.getRegistration());

        assertThat(found.size()).isEqualTo(1);
    }

    @Test
    public void whenFindBySearchParameters_available_thenReturnVehicleList() throws ParseException {

        Date s = format.parse("2019-04-10");
        Date e = format.parse("2019-04-20");

        // when
        List<Vehicle> found = vehicleRepository.findBySearchParameters("","","","",0.00,100000.00,s,e);

        // then
        assertThat(found.get(0).getId()).
                isEqualTo(vehicle1.getId());

        assertThat(found.size()).isEqualTo(1);
    }

    @Test
    public void whenFindBySearchParameters_notAvailable_thenReturnVehicleList() throws ParseException {

        Date s = format.parse("2019-03-10");
        Date e = format.parse("2019-04-20");

        // when
        List<Vehicle> found = vehicleRepository.findBySearchParameters("","","","",0.00,100000.00,s,e);

        // then
        assertThat(found).isEmpty();
    }

    @Test
    public void whenFindBySearchParameters_type_thenReturnVehicleList() throws ParseException {

        Date s = format.parse("2100-03-10");
        Date e = format.parse("2018-04-20");

        // when
        List<Vehicle> found = vehicleRepository.findBySearchParameters("","","","Luxury",0.00,100000.00,s,e,vehicles);

        // then
        assertThat(found.size()).isEqualTo(3);
    }

    @Test
    public void whenFindByLocationThenFindVehicleInRange(){

        // when
        List<Vehicle> found = vehicleRepository.findByLocation(-71.1,42.29,15.0);

        // then
        assertThat(found.get(0)).isEqualTo(vehicle1);
    }

    @Test
    public void whenFindByLocationOutOfRangeThenReturnEmptyList(){

        // when
        List<Vehicle> found = vehicleRepository.findByLocation(-70.1,40.29,15.0);

        // then
        assertThat(found).isEmpty();
    }

}
