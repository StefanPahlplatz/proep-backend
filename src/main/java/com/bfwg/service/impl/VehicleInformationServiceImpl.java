package com.bfwg.service.impl;

import com.bfwg.model.Image;
import com.bfwg.model.Vehicle;
import com.bfwg.model.VehicleEnrichmentResponse;
import com.bfwg.model.VehicleRequest;
import com.bfwg.service.VehicleInformationService;
import com.bfwg.service.VehicleService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class VehicleInformationServiceImpl implements VehicleInformationService {


    @Autowired
    private VehicleService vehicleService;

    private RestTemplate restfulTemplate;
    private String baseConnectionString;

    public VehicleInformationServiceImpl(){
        this.restfulTemplate = new RestTemplate();
        baseConnectionString = "https://opendata.rdw.nl/resource/m9d7-ebf2.json";
    }

    @Override
    public VehicleEnrichmentResponse EnrichVehicleData(VehicleRequest vehicle) {

        try{
            vehicle.setRegistration(vehicle.getRegistration().toUpperCase().trim());
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseConnectionString)
                    .queryParam("kenteken", vehicle.getRegistration());

            HttpEntity<String> response = restfulTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    String.class);

            if(response == null){
                return new VehicleEnrichmentResponse(
                        false,
                        null,
                        "Could not get a response from external API");
            }

            if(!((ResponseEntity<String>) response).getStatusCode().is2xxSuccessful()){
                return new VehicleEnrichmentResponse(
                    false,
                    null,
                    response.getBody());
            }

            JsonArray jsonArray = new JsonParser().parse(response.getBody()).getAsJsonArray();

            if(jsonArray.size() == 0){
                return new VehicleEnrichmentResponse(
                        false,
                        null,
                        "Could not find information about vehicle with license plate" +
                                 vehicle.getRegistration() +  "in external database");
            }

            Vehicle newVehicle =  new Vehicle();

            JsonObject object = jsonArray.get(0).getAsJsonObject();
            String carBrand = object.get("merk").getAsString();
            String carModel = object.get("handelsbenaming").getAsString();
            String carType = object.get("inrichting").getAsString();
            String color = object.get("eerste_kleur").getAsString();
            String numberOfDoors = object.get("aantal_deuren").getAsString();

            newVehicle.setMake(carBrand);
            newVehicle.setColour(color);
            newVehicle.setModel(carModel);
            newVehicle.setType(carType);
            newVehicle.setNumberOfDoors(Integer.parseInt(numberOfDoors));

            Set<Image> images = new HashSet<Image>();

            if(vehicle.getImageLinks() != null){
                for(String imagePath: vehicle.getImageLinks()){
                    Image newImage = new Image();
                    newImage.setPath(imagePath);
                    images.add(newImage);
                }

                newVehicle.setImages(images);
            }



            return new VehicleEnrichmentResponse(true, newVehicle, new String());

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new VehicleEnrichmentResponse (false, null, ex.getMessage());
        }

    }
}
