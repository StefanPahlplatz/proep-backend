package com.bfwg.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

    @Column(name = "price")
    private Double price;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "cancelled")
    private boolean cancelled;

    @Column(name = "returned")
    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="user_review")
    private Review userreview;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="vehicle_review")
    private Review vehiclereview;

    public Reservation(){}

    public Reservation(Date startdate, Date enddate, Double price, boolean paid, boolean cancelled, boolean returned,
                       User user, Vehicle vehicle, Review userreview, Review vehiclereview){
        this.startdate = startdate;
        this.enddate = enddate;
        this.price = price;
        this.paid = paid;
        this.cancelled = cancelled;
        this.returned = returned;
        this.user = user;
        this.vehicle = vehicle;
        this.userreview = userreview;
        this.vehiclereview = vehiclereview;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Review getUserreview() {
        return userreview;
    }

    public void setUserreview(Review userreview) {
        this.userreview = userreview;
    }

    public Review getVehiclereview() {
        return vehiclereview;
    }

    public void setVehiclereview(Review vehiclereview) {
        this.vehiclereview = vehiclereview;
    }
}
