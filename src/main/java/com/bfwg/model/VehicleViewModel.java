package com.bfwg.model;

import java.util.List;
import java.util.Set;

public class VehicleViewModel {

    private Long id;

    private String brand;

    private String model;

    private String type;

    private double price;

    private int mileage;

    private Set<Image> images;

    private String color;

    private UserViewModel user;

    public VehicleViewModel(Vehicle vehicle){

        this.id = vehicle.getId();
        this.brand = vehicle.getMake();
        this.model = vehicle.getModel();
        this.type = vehicle.getType();
        this.price = vehicle.getPrice();
        this.mileage = vehicle.getMileage();
        this.images = vehicle.getImages();
        this.color = vehicle.getColour();
        this.user = new UserViewModel(vehicle.getUser());
    }

    public VehicleViewModel(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UserViewModel getUser() {
        return user;
    }

    public void setUser(UserViewModel user) {
        this.user = user;
    }
}
