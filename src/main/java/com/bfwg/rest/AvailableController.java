package com.bfwg.rest;

import com.bfwg.model.Available;
import com.bfwg.model.Vehicle;
import com.bfwg.service.AvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> findByVehicle(Vehicle vehicle){
        List<Available> availables = this.availableService.findByVehicle(vehicle);

        if(availables.isEmpty()){
            return new ResponseEntity<>("There are no times available at the moment where this" +
                    " vehicle is free, please try again later", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(availables,HttpStatus.OK);
    }
}
