package com.bfwg.model;

import java.util.Set;

public class VehicleRequest {

    private Long vehicleId;

    private String registration;

    Set<String> imageLinks;

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getRegistration() {
        return registration;
    }

    public Set<String> getImageLinks() {
        return imageLinks;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setImageLinks(Set<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public VehicleRequest(Long vehicleId, String registration, Set<String> imageLinks) {
        this.vehicleId = vehicleId;
        this.registration = registration;
        this.imageLinks = imageLinks;
    }
}
