package com.bfwg.repository;

import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.stream.Location;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByUser(User user);
    List<Vehicle> findByMake(String make);
    List<Vehicle> findByModel(String model);
    Vehicle findByRegplate(String regplate);
    List<Vehicle> findByPriceBetween (Double pricelower, Double priceupper);
}
