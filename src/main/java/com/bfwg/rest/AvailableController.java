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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping( value="/api/available", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailableController {

    @Autowired
    AvailableService availableService;

    @RequestMapping(method = GET,value = "/")
    public List<Available> findByVehicle(Vehicle vehicle){

        return this.availableService.findByVehicle(vehicle);

    }
}
