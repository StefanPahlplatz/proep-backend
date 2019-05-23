package com.bfwg.rest;

import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.Image;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.service.ImageService;
import com.bfwg.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping( value="/api/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private VehicleService vehicleService;

    private final String uploadDirectory = "/static/images/";

    @RequestMapping( method = GET, value="/")
    public List<Image> getAllImages(){

        return this.imageService.findAll();
    }

    @RequestMapping(method = POST, value = "/{vehicleId}")
    public ResponseEntity<?> saveImage(@PathVariable (value = "vehicleId") Long vehicleId, @RequestBody MultipartFile file){

        Vehicle vehicle = this.vehicleService.findById(vehicleId);

        if(vehicle == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(file.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        try{
            Image image = new Image();

            image.setVehicle(vehicle);

            image = this.imageService.save(image);

            String fileName = "image"+image.getId()+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);

            file.getOriginalFilename().replace(file.getOriginalFilename(), fileName);

            Path path = Paths.get(new ClassPathResource(uploadDirectory).getURI());

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, path.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            image.setPath(path.toString()+"\\"+fileName);

            image = this.imageService.save(image);

            return new ResponseEntity<>(image, HttpStatus.CREATED);
        }
        catch (IOException e){
            return new ResponseEntity<>(e.getStackTrace(),HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{imageid}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "imageid") Long imageId) {

        Image image = this.imageService.findById(imageId);

        if(image == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(image.getVehicle().getUser().getId().equals(user.getId())){
            return ResponseEntity.badRequest().body("the user is not authorised for this action");
        }

        File file = new File(image.getPath());

        if(file.delete()){
            this.imageService.delete(image);

            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping (method = GET, value="/type")
    public List<Image> findByVehicle (@RequestBody Long vehicleId){

        Vehicle vehicle = this.vehicleService.findById(vehicleId);

        return this.imageService.findByVehicle(vehicle);
    }

}
