package com.bfwg.rest;

import com.bfwg.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@CrossOrigin
@RestController
@RequestMapping(value="/api/twitter", produces = MediaType.APPLICATION_JSON_VALUE)
public class TwitterController {

    @Autowired
    private TwitterService twitterService;

    @RequestMapping(method = GET, value = "/getcity/{city}")
    public java.util.List<String> getCity(@PathVariable String city) throws TwitterException {
        List<String> mapCity = new ArrayList<String>();
        mapCity = twitterService.searchTweet(city);
        return mapCity;
    }

    @RequestMapping(method = GET, value = "/recommendation")
    public ResponseEntity<?> getRecommendation() throws TwitterException{
        Map<String, String> city = twitterService.getRecommendation();
        if(city.values().isEmpty()){
            return new ResponseEntity<>("Cant connect to twitter ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "/count/{city}")
    public Integer getTweetsCount(@PathVariable String city) throws TwitterException {
        List<String> mapCity = new ArrayList<>();
        mapCity = twitterService.searchTweet(city);
        Integer x = twitterService.numberOfTweet(mapCity);
        return x;
    }
}
