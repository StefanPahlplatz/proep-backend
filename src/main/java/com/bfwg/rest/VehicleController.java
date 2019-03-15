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
import javax.ws.rs.core.GenericEntity;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping( value="/api/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @RequestMapping( method = GET, value="/all")
    public ResponseEntity<?> getAllVehicles(){
        List<Vehicle> vehicles = this.vehicleService.findAll();

        if(vehicles.isEmpty()){
            return new ResponseEntity<>("There were no vehicles in the database",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicles,HttpStatus.OK);
    }

    @RequestMapping( method = GET, value="/id/{vehicleid}")
    public ResponseEntity findById(@PathVariable (value = "vehicleid")Long vehicleid){
        Vehicle vehicle = this.vehicleService.findById(vehicleid);
        if(vehicle == null){
            return new ResponseEntity<>("There were no vehicles with the id "+vehicleid,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle,HttpStatus.OK);
    }

    @RequestMapping (method = GET, value="/make/{vehiclemake}")
    public ResponseEntity<?> findByMake(@PathVariable (value = "vehiclemake")String vehiclemake){
        List<Vehicle> vehicles = this.vehicleService.findByMake(vehiclemake);
        if(vehicles.isEmpty()){
            return new ResponseEntity<>("There were no vehicles with the car make "+vehiclemake,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicles,HttpStatus.OK);
    }

    @RequestMapping (method = POST, value="/user")
    public ResponseEntity<?> findByOwner (@RequestBody User user){

        List<Vehicle> vehicles = this.vehicleService.findByUser(user);
        if(vehicles.isEmpty()){
            return new ResponseEntity<>("The user "+user.getUsername()+" owns no cars",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicles,HttpStatus.OK);
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
    public ResponseEntity updateVehicle(@PathVariable (value = "vehicleid") Long vehicleid,
                                 @Valid @RequestBody Vehicle vehiclerequest){
        Vehicle existVehicle = this.vehicleService.findById(vehicleid);

        if(existVehicle == null){
            return new ResponseEntity<>("there was no vehicle with the id "+vehicleid,HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(existVehicle.getId());
        vehicle.setMileage(existVehicle.getMileage());
        vehicle.setColour(existVehicle.getColour());
        vehicle.setModel(existVehicle.getModel());
        vehicle.setMake(existVehicle.getMake());
        vehicle.setRegplate(existVehicle.getRegplate());
        vehicle.setPrice(existVehicle.getPrice());
        vehicle.setAvailables(existVehicle.getAvailables());
        vehicle.setUser(existVehicle.getUser());

        return new ResponseEntity<>(vehicle,HttpStatus.OK);
    }

    @RequestMapping("/price/lower/{pricelower}/upper/{priceupper}")
    public ResponseEntity<?> findByPriceBetween(@PathVariable (value = "pricelower") Double pricelower,
                                            @PathVariable (value = "priceupper") Double priceupper){
        List<Vehicle> vehicles = this.vehicleService.findByPriceBetween(pricelower,priceupper);
        if(vehicles.isEmpty()){
            return new ResponseEntity<>("no vehicles found between the price of "+pricelower+" and "+priceupper,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicles,HttpStatus.OK);
    }

    @RequestMapping("/date/start/{startdate}/end/{enddate}")
    public ResponseEntity<?> findByPriceBetween(@PathVariable (value = "startdate") Date startdate,
                                            @PathVariable (value = "enddate") Date enddate){
        List<Vehicle> vehicles = this.vehicleService.findByAvailablesStartdateBeforeAndAvailablesEnddateAfter(startdate,enddate);
        if(vehicles.isEmpty()){
            return new ResponseEntity<>("No vehicles found between the dates "+startdate.toString()+" and "+enddate.toString(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicles,HttpStatus.OK);
    }



}
