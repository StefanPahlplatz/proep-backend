package com.bfwg.service.impl;

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
        Vehicle vehicle = new Vehicle();
        vehicle.setRegplate(vehiclerequest.getRegplate());
        vehicle.setMake(vehiclerequest.getMake());
        vehicle.setModel(vehiclerequest.getModel());
        vehicle.setColour(vehiclerequest.getColour());
        vehicle.setMileage(vehiclerequest.getMileage());
        this.vehicleRepository.save(vehicle);
        return vehicle;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(Vehicle vehicle) throws AccessDeniedException {
        this.vehicleRepository.delete(vehicle);
    }

    @Override
    public Vehicle findByRegplate(String regplate) {
        return this.vehicleRepository.findByRegplate(regplate);
    }

    @Override
    public List<Vehicle> findByPriceBetween(Double pricelower, Double priceupper) {
        return this.vehicleRepository.findByPriceBetween(pricelower,priceupper);
    }

    @Override
    public List<Vehicle> findByAvailablesStartdateBeforeAndAvailablesEnddateAfter(Date startdate, Date enddate) {
        return this.vehicleRepository.findByAvailablesStartdateBeforeAndAvailablesEnddateAfter(startdate,enddate);
    }


}
