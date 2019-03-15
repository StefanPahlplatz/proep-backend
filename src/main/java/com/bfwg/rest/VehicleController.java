package com.bfwg.rest;


import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.ws.Response;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping( value="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @RequestMapping( method = GET, value="/vehicles/all")
    public List<Vehicle> getAllVehicles(){
        return this.vehicleService.findAll();
    }

    @RequestMapping( method = GET, value="/vehicles/{vehicleid}")
    public Vehicle findById(Long vehicleid){
        return this.vehicleService.findById(vehicleid);
    }

    @RequestMapping (method = GET, value="/vehicles/make/{vehiclemake}")
    public List<Vehicle> findByMake(String vehiclemake){
        return this.vehicleService.findByMake(vehiclemake);
    }

    @RequestMapping (method = POST, value="/vehicles/user")
    public List<Vehicle> findByOwner (@RequestBody User owner){
        return this.vehicleService.findByOwner(owner);
    }

    @RequestMapping(method = POST, value = "/vehicles/")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehiclerequest){
        Vehicle existVehicle = this.vehicleService.findByVin(vehiclerequest.getVin());
        if(existVehicle != null){
            throw new ResourceConflictException(vehiclerequest.getId(), "A vehicle with this vin already exists!");
        }
        Vehicle vehicle = this.vehicleService.save(vehiclerequest);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/vehicles/{vehicleid}")
    public Vehicle updateVehicle(@PathVariable (value = "vehicleid") Long vehicleid,
                                 @Valid @RequestBody Vehicle vehiclerequest){
        Vehicle existVehicle = this.vehicleService.findById(vehicleid);
        if(existVehicle == null){
            return null;///throw new ResourceNotFoundException("VehicleId " + vehicleid + " not found"); not working
        }
        Vehicle vehicle = this.vehicleService.save(vehiclerequest);
        return vehicle;
    }
}
