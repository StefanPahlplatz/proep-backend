package com.bfwg.service;

import com.bfwg.model.Available;
import com.bfwg.model.Vehicle;

import java.util.Date;
import java.util.List;

public interface AvailableService {

    List<Available> findByVehicle(Vehicle vehicle);
    List<Available> findByStartdateBeforeAndEnddateAfter(Date start, Date end);
    Available save(Available availablerequest);
    Available findByStartdateBeforeAndEnddateAfterAndVehicle(Date start,Date end,Vehicle vehicle);
    void delete(Available available);
}
