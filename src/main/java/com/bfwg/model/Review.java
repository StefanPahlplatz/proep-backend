package com.bfwg.model;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "review")
public class Review implements Serializable {

    @Id
    @NotNull
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "type")
    private String type;

    public Review(){

    }

    public Review(Double rating, String comment, String type){
        this.rating = rating;
        this.comment = comment;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reservation=" + reservation +
                ", user=" + user +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
