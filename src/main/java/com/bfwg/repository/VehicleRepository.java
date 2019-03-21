package com.bfwg.repository;

import com.bfwg.model.Available;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.xml.stream.Location;
import java.util.Date;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByUser(User user);
    List<Vehicle> findByMake(String make);
    List<Vehicle> findByModel(String model);
    Vehicle findByRegistration(String registration);
    List<Vehicle> findByPriceBetween (Double pricelower, Double priceupper);

    @Query("select v from Vehicle v where v.colour = ?1 and v.make = ?2 and v.model = ?3 and v.price" +
            " between ?4 and ?5 and v.availables in ?6")
    List<Vehicle> findBySearchParameters(String colour,String make,String model,
                                         Double minprice, Double maxprice, List<Available> availables);
}
