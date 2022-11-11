package com.rakkiics3560.minitwitter;

/** 
 * Interface for messages, including tweets.
 * @author Rakkii
 */
public interface Message {
    public User getAuthor();
    public String getMessage();
    // get timestamp
    // maybe not for Assignment 2 yet but for sure for Assignment 3
}
