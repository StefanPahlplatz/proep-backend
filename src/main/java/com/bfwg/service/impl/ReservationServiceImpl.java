package com.bfwg.service.impl;

import com.bfwg.model.Available;
import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.repository.AvailableRepository;
import com.bfwg.repository.ReservationRepository;
import com.bfwg.service.AvailableService;
import com.bfwg.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AvailableService availableService;

    @Override
    public List<Reservation> findAll() {
        return this.reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findByUser_Id(Long id) {
        return this.reservationRepository.findByUser_Id(id);
    }

    @Override
    public List<Reservation> findByVehicle_Id(Long id) {
        return this.reservationRepository.findByVehicle_Id(id);
    }

    @Override
    public Reservation findById(Long id) {
        return this.reservationRepository.findOne(id);
    }

    @Override
    public Reservation save(Reservation reservation) {

        Available available = this.availableService.findByStartdateBeforeAndEnddateAfterAndVehicle(reservation.getStartdate(),reservation.getEnddate(),reservation.getVehicle());

        if(available == null){
            return null;
        }

        //when the reservation start and end dates match the available entity of the vehicle
        if(available.getStartdate().equals(reservation.getStartdate()) && available.getEnddate().equals(reservation.getEnddate())){
            this.availableService.delete(available);
            return this.reservationRepository.save(reservation);
        }
        //when the available start date is before the reservation start date and the available end date
        //is after the reservation end date.
        else if(available.getStartdate().before(reservation.getStartdate()) && available.getEnddate().after(reservation.getEnddate())){
            available.setEnddate(reservation.getStartdate());
            this.availableService.save(available);
            this.availableService.save(new Available(reservation.getEnddate(),available.getEnddate(),reservation.getVehicle()));
            return this.reservationRepository.save(reservation);
        }
        //when the available start date is before the reservation start date and the available end date
        //is the same as the reservation end date
        else if(available.getStartdate().before(reservation.getStartdate()) && available.getEnddate().equals(reservation.getEnddate())){
            available.setEnddate(reservation.getStartdate());
            this.availableService.save(available);
            return this.reservationRepository.save(reservation);
        }
        //when the available end date is after the reservation end date and the available start date
        //is the same as the reservation start date
        else if(available.getEnddate().after(reservation.getEnddate()) && available.getStartdate().equals(reservation.getStartdate())){
            available.setStartdate(reservation.getEnddate());
            this.availableService.save(available);
            return this.reservationRepository.save(reservation);
        }
        else{
            return null;
        }
    }

    @Override
    public void delete(Reservation reservation) {

        this.availableService.save(new Available(reservation.getStartdate(),reservation.getEnddate(),reservation.getVehicle()));

        this.reservationRepository.delete(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {

        Reservation temp = findById(reservation.getId());

        return this.reservationRepository.save(reservation);
    }
}
