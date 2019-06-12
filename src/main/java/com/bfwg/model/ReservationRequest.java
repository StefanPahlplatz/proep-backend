package com.bfwg.model;

public class ReservationRequest {

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private Long userId;

    private Long vehicleId;

    private String startDate;

    private String endDate;

    private double price;

    public Long getUserId() {
        return userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }

    public ReservationRequest(Long userId, Long vehicleId, String startDate, String endDate, double price) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public ReservationRequest(){

    }




}
