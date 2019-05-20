package com.bfwg.rest;

import com.bfwg.model.Available;
import com.bfwg.model.Vehicle;
import com.bfwg.service.AvailableService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping( value="/api/available", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailableController {

    @Autowired
    AvailableService availableService;

    @RequestMapping(method = GET,value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Available> findByVehicle(Vehicle vehicle){
        return this.availableService.findByVehicle(vehicle);
    }

    @RequestMapping(method = GET, value = "/date")
    public List<Available> findByStartdateBeforeAndEnddateAfter
            (Date startDate, Date endDate){
        return availableService.findByStartdateBeforeAndEnddateAfter(startDate, endDate);
    }

    @RequestMapping(method = GET, value = "/dateandvehicle")
    public Available findByStartdateBeforeAndEnddateAfterAndVehicle
            (Date startDate, Date endDate, Vehicle vehicle){
        return availableService.findByStartdateBeforeAndEnddateAfterAndVehicle(
                startDate, endDate, vehicle);
    }

    @RequestMapping(method = PUT, value = "/save")
    public Available save(Available availableRequest){
        return availableService.save(availableRequest);
    }

    @RequestMapping(method = DELETE, value = "/delete")
    public void delete(Available availableRequest){
        availableService.delete(availableRequest);
    }
}
