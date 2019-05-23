package com.bfwg.service;

import java.awt.*;
import java.net.MalformedURLException;

public interface GeocodingService {

    Point findPointByCity(String city) throws Exception;

    String findAddressByPosition(Double longitude, Double latitude) throws Exception;
}
