package com.bfwg.model;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public class UserViewModel {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Double rating;

    private String address;

    private String city;

    private String email;

    private String telephone;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public UserViewModel(){

    }

    public UserViewModel(User user){
        this.id = user.getId();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.email = user.getEmail();
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.rating = user.getRating();
        this.telephone = user.getTelephone();
        this.username = user.getUsername();
    }





}
