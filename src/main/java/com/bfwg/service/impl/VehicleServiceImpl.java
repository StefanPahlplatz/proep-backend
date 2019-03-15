package com.bfwg.service.impl;

import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.repository.VehicleRepository;
import com.bfwg.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findByOwner(User owner) {
        List<Vehicle> vehicles = vehicleRepository.findByOwner(owner);
        return vehicles;
    }

    @Override
    public List<Vehicle> findByMake(String make) {
        List<Vehicle> vehicles = vehicleRepository.findByMake(make);
        return vehicles;
    }

    @Override
    public List<Vehicle> findByModel(String model) {
        List<Vehicle> vehicles = vehicleRepository.findByModel(model);
        return vehicles;
    }

    @Override
    public Vehicle findById(Long id) {
        Vehicle vehicle = vehicleRepository.findOne(id);
        return vehicle;
    }

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }

    @Override
    public Vehicle save(Vehicle vehiclerequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(vehiclerequest.getVin());
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
    public Vehicle findByVin(String vin) {
        return this.vehicleRepository.findByVin(vin);
    }


}
