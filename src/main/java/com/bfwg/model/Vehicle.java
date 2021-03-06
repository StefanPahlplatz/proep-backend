package com.bfwg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.*;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", insertable = false, updatable = false)
    @CreationTimestamp
    private Date timestamp;

    @Column(name = "registration")
    private String registration;

    @Column(name = "colour")
    private String colour;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private Double price;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "numberOfDoors")
    private Integer numberOfDoors;

    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    @Transient
    private Boolean isRented;

    @Transient
    private int timesRented;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "vehicle",fetch = FetchType.EAGER)
    private Set<Available> availables;

    @OneToMany(mappedBy = "vehicle",fetch = FetchType.EAGER)
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "vehicle",fetch = FetchType.EAGER)
    private Set<Image> images;

    public Vehicle(){

    }

    public Vehicle(String make, String model,String type, int mileage, String registration, String colour, Double price, User user){
        this.make = make;
        this.model = model;
        this.type = type;
        this.registration = registration;
        this.mileage = mileage;
        this.colour = colour;
        this.price = price;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getRented() {

        Date now = new Date();

        if(reservations == null)
            return false;

        for (Reservation r: reservations) {
            if(r.getStartdate().before(now) && r.getEnddate().after(now)){
                return true;
            }
        }

        return false;
    }

    public void setRented(Boolean rented) {
        isRented = rented;
    }

    public int getTimesRented() {

        int counter = 0;

        if(reservations == null)
            return 0;

        for (Reservation r: reservations) {
            if(r.getStartdate().before(new Date())){
                counter++;
            }
        }

        return counter;
    }

    public void setTimesRented(int timesRented) {
        this.timesRented = timesRented;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Available> getAvailables() {
        return availables;
    }

    public void setAvailables(Set<Available> availables) {
        this.availables = availables;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
