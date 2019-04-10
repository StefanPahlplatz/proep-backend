package com.bfwg.service.impl;

import com.bfwg.model.Available;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.repository.VehicleRepository;
import com.bfwg.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findByUser(User user) {
        return vehicleRepository.findByUser(user);
    }

    @Override
    public List<Vehicle> findByMake(String make) {
        return vehicleRepository.findByMake(make);
    }

    @Override
    public List<Vehicle> findByModel(String model) {
        return vehicleRepository.findByModel(model);
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findOne(id);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle save(Vehicle vehiclerequest) {
        return this.vehicleRepository.save(vehiclerequest);
    }

    @Override
    public void delete(Vehicle vehicle){
        this.vehicleRepository.delete(vehicle);
    }

    @Override
    public List<Vehicle> findByType(String type) {
        return this.vehicleRepository.findByType(type);
    }

    @Override
    public Vehicle findByRegistration(String registration) {
        return this.vehicleRepository.findByRegistration(registration);
    }

    @Override
    public List<Vehicle> findByPriceBetween(Double pricelower, Double priceupper) {
        return this.vehicleRepository.findByPriceBetween(pricelower,priceupper);
    }

    @Override
    public List<Vehicle> findBySearchParameters(String colour, String make, String model,String type, Double minprice, Double maxprice, Date startdate, Date enddate) {
        return this.vehicleRepository.findBySearchParameters(colour,make,model,type, minprice, maxprice, startdate, enddate);
    }


}
