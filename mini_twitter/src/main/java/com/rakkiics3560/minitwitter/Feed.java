package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 
 * Contains a user's feed and helper methods surrounding feeds.
 * @author Rakkii
 */
public class Feed {
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
     * Merges current feed with another given feed.
     * @param feed Given feed to merge with this.tweetMap
     */
    public void mergeFeed(Feed feed) {
        this.tweetMap = Stream.of(this.tweetMap, feed.getTweetMap())
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

    /**
     * Takes the tweet map in feed and returns a sorted tweet list.
     * @return List of tweets in reverse chronological order.
     */
    public List<Tweet> getRevChronoTweetList() {
        List<Tweet> tweetList = new ArrayList<>();
        PriorityQueue<Map.Entry<Integer, Tweet>> maxHeap = 
                new PriorityQueue<>(
            (a, b) -> b.getKey() - a.getKey()
        );

        for (Map.Entry<Integer, Tweet> entry : tweetMap.entrySet()) {
            maxHeap.offer(entry);
        }

        while (!maxHeap.isEmpty()) {
            tweetList.add(maxHeap.poll().getValue());
        }

        return tweetList;
    }

    /* Not needed but might be helpful later
    public void removeUnfollowedUser(User user) {
        for (Map.Entry<Integer, Tweet> entry : tweetMap.entrySet()) {
            if (entry.getValue().getAuthor() == user) {
                tweetMap.remove(entry.getKey());
            }
        }
    }
    */
}
