package com.bfwg.service;

import com.bfwg.model.Location;

public interface GeocodingService {

    Location findPointByCity(String city) throws Exception;

    String findAddressByPosition(Double longitude, Double latitude) throws Exception;
}
