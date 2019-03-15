package com.bfwg.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.GregorianCalendar;

@Entity
@Table(name = "VEHICLE")
public class Vehicle implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vin")
    private String vin;

    @Column(name = "colour")
    private String colour;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "owner")
    private User owner;

    public Vehicle(){}

    public Vehicle(String make, String model, int mileage, String vin, String colour, User owner){
        this.make = make;
        this.model = model;
        this.vin = vin;
        this.mileage = mileage;
        this.colour = colour;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
