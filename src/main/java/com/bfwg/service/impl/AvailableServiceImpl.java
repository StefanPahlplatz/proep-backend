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

    @Override
    public Available save(Available availablerequest) {

        return this.availableRepository.save(availablerequest);
    }

    @Override
    public Available findByStartdateBeforeAndEnddateAfterAndVehicle(Date start, Date end, Vehicle vehicle) {
        return this.availableRepository.findByStartdateBeforeAndEnddateAfterAndVehicle(start,end,vehicle);
    }

    @Override
    public void delete(Available available) {
        this.availableRepository.delete(available);
    }


}
