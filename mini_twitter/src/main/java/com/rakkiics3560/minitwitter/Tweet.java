package com.rakkiics3560.minitwitter;

/** 
 * 
 */
public class Tweet implements Message {
    private static int idCounter = 0;
    private int tweetId;
    private User author;
    private String message;
    // private timestamp coming soon

    public Tweet(User user, String text) {
        tweetId = idCounter;
        author = user;
        message = text;
        idCounter++;
    }

    public int getTweetId() {
        return tweetId;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public String getMessage() {
        return message;
    }

    // getTimestamp() coming soon
}
