package com.bfwg.service;

import twitter4j.TwitterException;

import java.util.List;
import java.util.Map;

public interface TwitterService {

    List<String> searchTweet(String city) throws TwitterException;

    Integer numberOfTweet(List<String> tweets);

    Map<String, String> getRecommendation() throws TwitterException;


}
