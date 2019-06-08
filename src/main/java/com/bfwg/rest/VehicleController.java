package com.bfwg.rest;


import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.service.AvailableService;
import com.bfwg.service.GeocodingService;
import com.bfwg.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController
@RequestMapping( value="/api/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    AvailableService availableService;

    @Autowired
    GeocodingService geocodingService;

    @RequestMapping( method = GET, value="/")
    public List<Vehicle> getAllVehicles(){

        return this.vehicleService.findAll();

    }

    @RequestMapping( method = GET, value="/{vehicleid}")
    public ResponseEntity findById(@PathVariable (value = "vehicleid")Long vehicleid){
        Vehicle vehicle = this.vehicleService.findById(vehicleid);
        if(vehicle == null){
            return new ResponseEntity<>("There were no vehicles with the id "+vehicleid,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle,HttpStatus.OK);
    }

    @RequestMapping (method = GET, value="/user")
    public List<Vehicle> findByOwner (@RequestBody User user){

        return this.vehicleService.findByUser(user);
    }

    @RequestMapping (method = GET, value="/type")
    public List<Vehicle> findByType (@RequestBody String type){

        return this.vehicleService.findByType(type);
    }

    @RequestMapping(method = POST, value = "/")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehiclerequest){
        Vehicle existVehicle = this.vehicleService.findByRegistration(vehiclerequest.getRegistration());
        if(existVehicle != null){
            throw new ResourceConflictException(vehiclerequest.getId(), "A vehicle with this registration already exists!");
        }
        Vehicle vehicle = this.vehicleService.save(vehiclerequest);

        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/{vehicle}")
    public ResponseEntity updateVehicle(@PathVariable (value = "vehicle") Vehicle vehicle){
        Vehicle existVehicle = this.vehicleService.findById(vehicle.getId());

        if(existVehicle == null){
            return new ResponseEntity<>("there was no vehicle with the id "+vehicle.getId(),HttpStatus.NOT_FOUND);
        }

        this.vehicleService.save(existVehicle);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @RequestMapping("/search")
    public List<Vehicle> findBySearchParameters(@RequestParam(value = "maxprice", defaultValue = "100000000") Double maxprice,
                                                @RequestParam(value = "minprice", defaultValue = "0") Double minprice,
                                                @RequestParam(value = "make", defaultValue = "") String make,
                                                @RequestParam(value = "model", defaultValue = "") String model,
                                                @RequestParam(value = "type", defaultValue = "") String type,
                                                @RequestParam(value = "colour", defaultValue = "") String colour,
                                                @RequestParam(value = "longitude", defaultValue = "0.0") Double longitude,
                                                @RequestParam(value = "latitude", defaultValue = "0.0") Double latitude,
                                                @RequestParam(value = "distance", defaultValue = "0.0") Double distance,
                                                @RequestParam(value = "start", defaultValue = "2100-12-31") String start,
                                                @RequestParam(value = "end", defaultValue = "2019-01-01") String end) throws ParseException {

        List<Vehicle> vehicles;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date s = format.parse(start);
        Date e = format.parse(end);

        if(longitude != 0.0 && latitude != 0.0 && distance != 0.0){
            vehicles = this.vehicleService.findByLocation(longitude,latitude,distance);
        }
        else{
            return this.vehicleService.findBySearchParameters(colour,make,model,type,minprice,maxprice,s,e);
        }

        if(vehicles.size() == 0)
            return new ArrayList<>();

        return this.vehicleService.findBySearchParameters(colour,make,model,type,minprice,maxprice,s,e,vehicles);
    }

    //region must be either US ("us1") or Europe ("eu1")
    @RequestMapping("/city/{city}")
    public List<Vehicle> findByCity(@PathVariable(value = "city") String city){

        Point point;

        try{
            point = this.geocodingService.findPointByCity(city);
        }
        catch(Exception e){
            return new ArrayList<>();
        }

        return vehicleService.findByLocation(point.getY(),point.getX(),15.0);
    }


    @DeleteMapping("/{vehicleid}")
    public ResponseEntity<?> deleteVehicle(@PathVariable(value = "vehicleid") Long vehicleId) {

        Vehicle vehicle = this.vehicleService.findById(vehicleId);

        if(vehicle == null){
            return new ResponseEntity<>("there was no vehicle with that id please refresh the page", HttpStatus.NOT_FOUND);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(vehicle.getUser().getId().equals(user.getId())){
            return ResponseEntity.badRequest().body("the user is not authorised for this action");
        }

        this.vehicleService.delete(vehicle);

        return ResponseEntity.noContent().build();
    }

}
