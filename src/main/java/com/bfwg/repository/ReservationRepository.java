package com.bfwg.repository;

import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findByUser_Id(Long id);

    List<Reservation> findByVehicle_Id(Long id);
}
