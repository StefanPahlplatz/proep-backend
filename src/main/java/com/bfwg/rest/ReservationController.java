package com.bfwg.rest;

import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.security.auth.IAuthenticationFacade;
import com.bfwg.service.ReservationService;
import com.bfwg.service.UserService;
import com.bfwg.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController
@RequestMapping( value="/api/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    IAuthenticationFacade authenticationFacade;

    @Autowired
    UserService userService;

    @Autowired
    VehicleService vehicleService;

    @RequestMapping( method = GET, value="/")
    public List<Reservation> getAllReservations(){

        return this.reservationService.findAll();
    }

    @RequestMapping( method = GET, value = "/owner/{ownerid}")
    public List<Reservation> findByCustomer(@PathVariable(value = "ownerid") Long id){

        return this.reservationService.findByUser_Id(id);
    }

    @RequestMapping( method = GET, value = "/vehicle/{vehicleid}")
    public List<Reservation> findByVehicle(@PathVariable(value = "vehicleid") Long id){

        return this.reservationService.findByVehicle_Id(id);
    }

    @RequestMapping( method = GET, value = "/{id}")
    public ResponseEntity findById(@PathVariable(value = "id")Long id){
        Reservation reservation = this.reservationService.findById(id);

        if(reservation == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "/user/{userid}/vehicle/{vehicleid}")
    public ResponseEntity<?> addReservation(@Valid @RequestBody Reservation reservationrequest,
                                            @PathVariable(value = "userid") Long userid,
                                            @PathVariable(value = "vehicleid") Long vehicleid){

        User user = this.userService.findById(userid);

        Vehicle vehicle = this.vehicleService.findById(vehicleid);

        reservationrequest.setUser(user);

        reservationrequest.setVehicle(vehicle);

        Reservation reservation = this.reservationService.save(reservationrequest);

        if(reservation != null){
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        }
        else{
            //the vehicle is not available at the specified dates
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{reservationid}")
    public ResponseEntity<?> deleteReservation(@PathVariable(value = "reservationid") Long reservationId) {

        Reservation reservation = this.reservationService.findById(reservationId);

        if(reservation == null){
            return new ResponseEntity<>("there was no reservation in the system, please refresh the page", HttpStatus.NOT_FOUND);
        }

        User user = (User) this.authenticationFacade.getPrincipal();
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(reservation.getUser().getUsername().equals(user.getUsername())){
            this.reservationService.delete(reservation);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body("the user is not authorised for this action");
    }


}
