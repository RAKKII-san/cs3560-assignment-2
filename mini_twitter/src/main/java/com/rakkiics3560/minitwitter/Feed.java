package com.rakkiics3560.minitwitter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Feed {
     // maybe using PriorityQueue might be better?
    Map<Integer, Tweet> tweetMap;

    public Feed() {
        tweetMap = new HashMap<>();
    }

    public void addToFeed(Tweet tweet) {
        int tweetId = tweet.getTweetId();
        try {
            if (tweetMap.containsKey(tweetId)) {
                throw new IllegalStateException(
                    "Duplicate TweetId found"
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        tweetMap.put(tweetId, tweet);
    }

    /**
     * 
     * @param f1
     * @param f2
     */
    public void mergeFeed(Feed f1, Feed f2) {
        tweetMap = Stream.of(f1.getTweetMap(), f2.getTweetMap())
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue,
                (v1, v2) -> new Tweet(v2.getAuthor(), v2.getMessage())
            )
        );
    }

    public Map<Integer, Tweet> getTweetMap() {
        return tweetMap;
    }

    // helper methods involving users and visitors/observers
}
