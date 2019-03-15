package com.bfwg.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "VEHICLE")
public class Vehicle implements Serializable {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "regplate")
    private String regplate;

    @Column(name = "colour")
    private String colour;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "vehicle")
    private List<Available> availables;



    public Vehicle(){}

    public Vehicle(String make, String model, int mileage, String regplate, String colour,Double price, User user){
        this.make = make;
        this.model = model;
        this.regplate = regplate;
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

    public String getRegplate() {
        return regplate;
    }

    public void setRegplate(String regplate) {
        this.regplate = regplate;
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

    public List<Available> getAvailables() {
        return availables;
    }

    public void setAvailables(List<Available> availables) {
        this.availables = availables;
    }
}
