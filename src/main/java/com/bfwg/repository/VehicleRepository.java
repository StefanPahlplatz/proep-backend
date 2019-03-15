package com.bfwg.repository;

import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByOwner(User owner);
    List<Vehicle> findByMake(String make);
    List<Vehicle> findByModel(String model);
    Vehicle findByVin(String vin);
}
