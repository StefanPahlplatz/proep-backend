package com.bfwg.model;

import java.io.Serializable;

public class Location implements Serializable {

    private Double longitude;
    private Double latitude;

    public Location(){}

    public Location(Double longitude,Double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
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
}
