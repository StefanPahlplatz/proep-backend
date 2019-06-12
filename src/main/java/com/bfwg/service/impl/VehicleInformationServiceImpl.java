package com.bfwg.service.impl;

import com.bfwg.model.Vehicle;
import com.bfwg.model.VehicleEnrichmentResponse;
import com.bfwg.service.VehicleInformationService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class VehicleInformationServiceImpl implements VehicleInformationService {

    private RestTemplate restfulTemplate;
    private String baseConnectionString;

    public VehicleInformationServiceImpl(){
        this.restfulTemplate = new RestTemplate();
        baseConnectionString = "https://opendata.rdw.nl/resource/m9d7-ebf2.json";
    }

    @Override
    public VehicleEnrichmentResponse EnrichVehicleData(Vehicle vehicle) {

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
                        vehicle,
                        "Could not get a response from external API");
            }

            if(!((ResponseEntity<String>) response).getStatusCode().is2xxSuccessful()){
                return new VehicleEnrichmentResponse(
                    false,
                    vehicle,
                    response.getBody());
            }

            JsonArray jsonArray = new JsonParser().parse(response.getBody()).getAsJsonArray();

            if(jsonArray.size() == 0){
                return new VehicleEnrichmentResponse(
                        false,
                        vehicle,
                        "Could not find information about vehicle with license plate" +
                                 vehicle.getRegistration() +  "in external database");
            }

            JsonObject object = jsonArray.get(0).getAsJsonObject();
            String carBrand = object.get("merk").getAsString();
            String carModel = object.get("handelsbenaming").getAsString();
            String carType = object.get("inrichting").getAsString();
            String color = object.get("eerste_kleur").getAsString();
            Integer numberOfDoors = object.get("aantal_deuren").getAsInt();

            vehicle.setMake(carBrand);
            vehicle.setColour(color);
            vehicle.setModel(carModel);
            vehicle.setType(carType);
            vehicle.setNumberOfDoors(numberOfDoors);

            return new VehicleEnrichmentResponse(true, vehicle);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new VehicleEnrichmentResponse (false, vehicle, ex.getMessage());
        }

    }
}
