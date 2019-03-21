package com.bfwg.service.impl;

import com.bfwg.model.Available;
import com.bfwg.model.Vehicle;
import com.bfwg.repository.AvailableRepository;
import com.bfwg.service.AvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AvailableServiceImpl implements AvailableService {

    @Autowired
    private AvailableRepository availableRepository;

    @Override
    public List<Available> findByVehicle(Vehicle vehicle) {
        return this.availableRepository.findByVehicle(vehicle);
    }

    @Override
    public List<Available> findByStartdateBeforeAndEnddateAfter(Date start,Date end){
        return this.availableRepository.findByStartdateBeforeAndEnddateAfter(start,end);
    }
}
