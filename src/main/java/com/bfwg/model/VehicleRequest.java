package com.bfwg.model;

import java.util.Set;

public class VehicleRequest {

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    private Long userId;

    private String registration;

    public Double getPrice() {
        return price;
    }

    public int getMiledge() {
        return miledge;
    }



    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMiledge(int miledge) {
        this.miledge = miledge;
    }

    private Double price;

    private int miledge;

    Set<String> imageLinks;

    public String getRegistration() {
        return registration;
    }

    public Set<String> getImageLinks() {
        return imageLinks;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setImageLinks(Set<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public VehicleRequest(Long userId, String registration, Double price, int miledge, Set<String> imageLinks) {
        this.userId = userId;
        this.registration = registration;
        this.price = price;
        this.miledge = miledge;
        this.imageLinks = imageLinks;
    }

    public VehicleRequest(Long userId, String registration, Set<String> imageLinks) {
        this.userId = userId;
        this.registration = registration;
        this.imageLinks = imageLinks;
    }

    public VehicleRequest(Long userId, String registration){
        this.userId = userId;
        this.registration = registration;
    }

    public VehicleRequest(){

    }
}
