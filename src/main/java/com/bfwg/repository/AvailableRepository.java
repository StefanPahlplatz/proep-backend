package com.bfwg.repository;

import com.bfwg.model.Available;
import com.bfwg.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AvailableRepository extends JpaRepository<Available,Long> {
    List<Available> findByVehicle(Vehicle vehicle);
    List<Available> findByStartdateBeforeAndEnddateAfter(Date start, Date end);
}
