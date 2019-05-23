package com.bfwg.rest;

import com.bfwg.service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping( value="/api/geocoding", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeocodingController {

    @Autowired
    GeocodingService geocodingService;

    @RequestMapping( method = GET, value="/longitude/{lon}/latitude/{lat}")
    public ResponseEntity findAddressByPosition(@PathVariable(value = "lon")Double lon,
                                                @PathVariable(value = "lat")Double lat){

        try{
            String address = this.geocodingService.findAddressByPosition(lon,lat);

            return new ResponseEntity<>(address,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("there was a problem with the external api", HttpStatus.BAD_REQUEST);
        }
    }
}
