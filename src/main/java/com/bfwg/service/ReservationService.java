package com.bfwg.service;

import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.repository.ReservationRepository;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAll();
    List<Reservation> findByUser_Id(Long id);
    List<Reservation> findByVehicle_Id(Long id);
    Reservation findById(Long id);
    Reservation save(Reservation reservation);
    void delete(Reservation reservation);
    Reservation update(Reservation reservation);
}
