package com.bfwg.rest;

import com.bfwg.model.Available;
import com.bfwg.model.Vehicle;
import com.bfwg.service.AvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping( value="/api/available", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailableController {

    @Autowired
    AvailableService availableService;

    @RequestMapping(method = POST,value = "/")
    public List<Available> findByVehicle(Vehicle vehicle){
        return this.availableService.findByVehicle(vehicle);
    }
}
