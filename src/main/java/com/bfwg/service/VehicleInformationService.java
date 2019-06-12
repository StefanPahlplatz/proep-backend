package com.bfwg.service;

import com.bfwg.model.Vehicle;
import com.bfwg.model.VehicleEnrichmentResponse;
import com.bfwg.model.VehicleRequest;

public interface VehicleInformationService {

    VehicleEnrichmentResponse EnrichVehicleData(VehicleRequest vehicle);
}
