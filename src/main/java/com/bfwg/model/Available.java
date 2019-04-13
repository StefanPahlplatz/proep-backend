package com.bfwg.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "AVAILABLE")
public class Available implements Serializable {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vehicle vehicle;

    public Available(){

    }

    public Available(Date startdate, Date enddate, Vehicle vehicle){
        this.startdate = startdate;
        this.enddate = enddate;
        this.vehicle = vehicle;
    };

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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
