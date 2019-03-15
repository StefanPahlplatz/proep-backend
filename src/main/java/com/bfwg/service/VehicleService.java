package com.bfwg.service;

import com.bfwg.model.User;
import com.bfwg.model.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findByOwner(User owner);

    List<Vehicle> findByMake(String make);

    List<Vehicle> findByModel(String model);

    Vehicle findById(Long id);

    List<Vehicle> findAll();

    Vehicle save(Vehicle vehiclerequest);

    void delete(Vehicle vehicle);

    Vehicle findByVin(String vin);
}
