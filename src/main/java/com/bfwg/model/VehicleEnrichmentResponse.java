package com.bfwg.model;

public class VehicleEnrichmentResponse {

    public VehicleEnrichmentResponse(boolean enrichmentSuccess, Vehicle vehicle) {
        this.enrichmentSuccess = enrichmentSuccess;
        this.vehicle = vehicle;
    }

    public VehicleEnrichmentResponse(boolean enrichmentSuccess, Vehicle vehicle, String errorMessage) {
        this.enrichmentSuccess = enrichmentSuccess;
        this.vehicle = vehicle;
        this.errorMessage = errorMessage;
    }



    private boolean enrichmentSuccess;

    private Vehicle vehicle;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public boolean isEnrichmentSuccess() {
        return enrichmentSuccess;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setEnrichmentSuccess(boolean enrichmentSuccess) {
        this.enrichmentSuccess = enrichmentSuccess;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
