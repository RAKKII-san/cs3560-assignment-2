package com.rakkiics3560.minitwitter;

/** 
 * Generic visitor pattern structure, used for visiting tweets.
 * @author Rakkii
 */
public interface Visitor {
    public boolean visitTweet(Tweet tweet);
}
