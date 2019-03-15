package com.bfwg.rest;


import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping( value="/api/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @RequestMapping( method = GET, value="/all")
    public List<Vehicle> getAllVehicles(){
        return this.vehicleService.findAll();
    }

    @RequestMapping( method = GET, value="/id/{vehicleid}")
    public Vehicle findById(@PathVariable (value = "vehicleid")Long vehicleid){
        return this.vehicleService.findById(vehicleid);
    }

    @RequestMapping (method = GET, value="/make/{vehiclemake}")
    public List<Vehicle> findByMake(@PathVariable (value = "vehiclemake")String vehiclemake){
        return this.vehicleService.findByMake(vehiclemake);
    }

    @RequestMapping (method = POST, value="/user")
    public List<Vehicle> findByOwner (@RequestBody User user){
        return this.vehicleService.findByUser(user);
    }

    @RequestMapping(method = POST, value = "/")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehiclerequest){
        Vehicle existVehicle = this.vehicleService.findByRegplate(vehiclerequest.getRegplate());
        if(existVehicle != null){
            throw new ResourceConflictException(vehiclerequest.getId(), "A vehicle with this registration already exists!");
        }
        Vehicle vehicle = this.vehicleService.save(vehiclerequest);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/update/{vehicleid}")
    public Vehicle updateVehicle(@PathVariable (value = "vehicleid") Long vehicleid,
                                 @Valid @RequestBody Vehicle vehiclerequest){
        Vehicle existVehicle = this.vehicleService.findById(vehicleid);
        if(existVehicle == null){
            return null;///throw new ResourceNotFoundException("VehicleId " + vehicleid + " not found"); not working
        }
        return this.vehicleService.save(vehiclerequest);
    }

    @RequestMapping("/price/lower/{pricelower}/upper/{priceupper}")
    public List<Vehicle> findByPriceBetween(@PathVariable (value = "pricelower") Double pricelower,
                                            @PathVariable (value = "priceupper") Double priceupper){
        return this.vehicleService.findByPriceBetween(pricelower,priceupper);
    }


}
