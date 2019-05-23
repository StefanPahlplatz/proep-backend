package com.bfwg.service.impl;

import com.bfwg.service.TwitterService;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TwitterServiceImpl implements TwitterService {

    /**
     *
     * @param city the name of the city
     * @return the arraylist of tweets being searched
     * @throws TwitterException
     */
    @Override
    public List<String> searchTweet(String city) throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query(city + " AND (holiday OR travelling)" + " exclude:retweets");


        ArrayList<Status> tweets = new ArrayList<Status>();

        SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -12);
        String yesterday = sdf.format(calendar.getTime());
        //
        query.setSince(yesterday);
        query.setCount(100);
        query.lang("en");

        QueryResult result = twitter.search(query);
        tweets.addAll(result.getTweets());

        return result.getTweets().stream().
                map(item -> item.getText())
                .collect(Collectors.toList());
    }

    /**
     *
     * @param tweets
     * @return a number of tweets
     */

    @Override
    public Integer numberOfTweet(List<String> tweets){
        int x = tweets.size();
        return x;
    }

    /**
     *
     * @return city that has the most famous search
     * @throws TwitterException
     */
    @Override
    public Map<String, String> getRecommendation() throws TwitterException {
        String cities [] = new String[]{"Paris", "London", "Tokyo", "New York", "San Francisco", "Prague", "Florence", "Rio de Janeiro", "Amsterdam"};
        HashMap<String, Integer> famousCities = new HashMap<String, Integer>();
        for(int i = 0; i<cities.length; i++){
            List<String> totalTweets = searchTweet(cities[i]);
            famousCities.put(cities[i], totalTweets.size());
        }
        Map.Entry<String, Integer> maxEntry = null;

        for(Map.Entry<String, Integer> entry : famousCities.entrySet()){
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        Map<String, String> favCity = new HashMap<>();
        favCity.put("city", maxEntry.getKey());
        return favCity;
    }
}
