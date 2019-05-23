package com.bfwg.service.impl;

import com.bfwg.model.Image;
import com.bfwg.model.Vehicle;
import com.bfwg.repository.ImageRepository;
import com.bfwg.service.ImageService;
import com.bfwg.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
public class ImageServiceImplIntegration {

    /*@InjectMocks
    private ImageService imageService = new ImageServiceImpl();

    @MockBean
    private ImageRepository imageRepository;

    private Vehicle vehicle;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenMultipartFileUploadedThenSaveImage() throws Exception{

        Path path = Paths.get("C:\\Users\\GarethDavid\\Documents\\UNIVERSITY\\test.png");
        String name = "test.png";
        String originalFileName = "test.png";
        String contentType = "image/png";
        byte[] content = null;
        content = Files.readAllBytes(path);
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(2L);

        Image image = new Image();
        image.setId(2L);
        image.setVehicle(vehicle);


        Mockito.when(imageRepository.save(any(Image.class))).thenReturn(image);


        imageService.save(result,vehicle);

        assertThat(image.getId()).isEqualTo(image.getId());
    }*/
}
