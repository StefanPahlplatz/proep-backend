package com.bfwg.service.impl;

import com.bfwg.model.Image;
import com.bfwg.model.Vehicle;
import com.bfwg.repository.ImageRepository;
import com.bfwg.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public void delete(Image image) {
        this.imageRepository.delete(image);
    }

    @Override
    public Image findById(Long id) {
        return this.imageRepository.findOne(id);
    }

    @Override
    public List<Image> findAll() {
        return this.imageRepository.findAll();
    }

    @Override
    public List<Image> findByVehicle(Vehicle vehicle) {
        return this.imageRepository.findByVehicle(vehicle);
    }
}
