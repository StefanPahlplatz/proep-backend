package com.bfwg.service;

import com.bfwg.model.Available;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import java.util.Date;
import java.util.List;

public interface VehicleService {
    List<Vehicle> findByUser(User user);

    List<Vehicle> findByMake(String make);

    List<Vehicle> findByModel(String model);

    Vehicle findById(Long id);

    List<Vehicle> findAll();

    Vehicle save(Vehicle vehiclerequest);

    void delete(Vehicle vehicle);

    Vehicle findByRegistration(String registration);

    List<Vehicle> findByPriceBetween (Double pricelower, Double priceupper);

    List<Vehicle> findBySearchParameters(String colour,String make,String model, Double minprice, Double maxprice, List<Available> availables);
}
