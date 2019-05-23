package com.bfwg.repository;

import com.bfwg.model.Image;
import com.bfwg.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

    List<Image> findByVehicle(Vehicle vehicle);
}
