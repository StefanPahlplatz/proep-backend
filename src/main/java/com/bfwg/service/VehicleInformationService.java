package com.bfwg.service;

import com.bfwg.model.Vehicle;
import com.bfwg.model.VehicleEnrichmentResponse;

public interface VehicleInformationService {

    VehicleEnrichmentResponse EnrichVehicleData(Vehicle vehicle);
}
