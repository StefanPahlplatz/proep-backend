package com.bfwg.service;

import com.bfwg.model.Image;
import com.bfwg.model.Vehicle;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    Image save(Image image);

    void delete(Image image);

    Image findById(Long id);

    List<Image> findAll();

    List<Image> findByVehicle(Vehicle vehicle);
}
