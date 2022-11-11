package com.rakkiics3560.minitwitter;

/** 
 * Class containing a tweet's author, ID number, and content.
 * @author Rakkii
 */
public class Tweet implements Message, Visitable {
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

    /** Builds a string to be read by the UserView. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        sb.append(author);
        sb.append(": ");
        sb.append(message);
        return sb.toString();
    }

    @Override
    public boolean accept(Visitor vis) {
        return vis.visitTweet(this);
    }

    // getTimestamp() coming soon
}
