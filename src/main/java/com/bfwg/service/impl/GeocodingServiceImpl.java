package com.bfwg.service.impl;

import com.bfwg.service.GeocodingService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Service
public class GeocodingServiceImpl implements GeocodingService {

    private String key = "7395b7a1f75728";

    private final String forwardAddress = "https://eu1.locationiq.com/v1/search.php?key="+key+"&q=";

    private final String reverseAddress = "https://eu1.locationiq.com/v1/reverse.php?key="+key+"&lat=";

    public String getJson(URL url) throws Exception{

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type","application/json");

        int status = con.getResponseCode();

        String str = new String();

        if(status == 200){
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                str = str + inputLine;
            }

            in.close();
        }
        else{
            throw new Exception("exited with status code "+status);
        }

        return str;
    }

    public Point findPointByCity(String city) throws Exception {

        Point point = new Point();

        URL url = new URL(forwardAddress+city+"&format=json");

        String str = getJson(url);

        JSONArray obj = new JSONArray(str);

        JSONObject res = obj.getJSONObject(0);

        Double lon = res.getDouble("lon");

        Double lat = res.getDouble("lat");

        point.setLocation(lat,lon);

        return point;
    }

    @Override
    public String findAddressByPosition(Double longitude, Double latitude) throws Exception {

        String address = new String();

        URL url = new URL(reverseAddress+latitude+"&lon="+longitude+"&format=json");

        String str = getJson(url);

        JSONObject res = new JSONObject(str);

        address = res.getString("display_name");

        return address;
    }
}
