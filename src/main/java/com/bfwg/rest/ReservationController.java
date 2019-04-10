package com.bfwg.rest;

import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping( value="/api/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @RequestMapping( method = GET, value="/")
    public List<Reservation> getAllReservations(){

        return this.reservationService.findAll();
    }

    @RequestMapping( method = GET, value = "/{user}")
    public List<Reservation> findByCustomer(@PathVariable(value = "user") User user){

        return this.reservationService.findByUser(user);
    }

    @RequestMapping( method = GET, value = "/{vehicle}")
    public List<Reservation> findByVehicle(@PathVariable(value = "vehicle") Vehicle vehicle){

        return this.reservationService.findByVehicle(vehicle);
    }

    @RequestMapping( method = GET, value = "/{id}")
    public ResponseEntity findById(@PathVariable(value = "id")Long id){
        Reservation reservation = this.reservationService.findById(id);

        if(reservation == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "/")
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservationrequest){
        Reservation existReservation = this.reservationService.findById(reservationrequest.getId());

        if(existReservation != null){
            throw new ResourceConflictException(reservationrequest.getId(), "A reservation with this id already exists!");
        }
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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(reservation.getUser().getId().equals(user.getId())){
            return ResponseEntity.badRequest().body("the user is not authorised for this action");
        }

        this.reservationService.delete(reservation);

        return ResponseEntity.noContent().build();
    }


}
