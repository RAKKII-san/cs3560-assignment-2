/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.List;

import com.rakkiics3560.minitwitter.visitors.Visitor;

/**
 * Basic functions checklist #2, #4, #5
 * @author RAKKII
 */
public class User extends Subject implements Observer, SysEntry {
    private String userId;
    private List<User> followersList;
    private List<User> followingList;

    private String groupName;
    private Feed personalFeed;
    private Feed newsFeed;

    public User(String name) {
        userId = name;
        followersList = new ArrayList<>();
        followingList = new ArrayList<>();
        observers = new ArrayList<>();
        personalFeed = new Feed();
        newsFeed = new Feed();
        setAllowsChildren(false);
        groupName = "Root";
    }

    public void followUser(User user) {
        followingList.add(user);
        user.getFollowersList().add(this);
        newsFeed.mergeFeed(user.getPersonalFeed());
        this.attach(user);
    }

    public void unfollowUser(User user) {
        if (followingList.contains(user)) {
            followingList.remove(user);
            user.getFollowersList().remove(this);
            newsFeed.removeUnfollowedUser(user);
            this.detach(user);
        }
    }

    public List<User> getFollowersList() {
        return followersList;
    }

    public List<User> getFollowingList() {
        return followingList;
    }

    public String getGroup() {
        return groupName;
    }

    public Feed getPersonalFeed() {
        return personalFeed;
    }

    public Feed getNewsFeed() {
        return newsFeed;
    }

    public boolean inGroup() {
        return groupName != "Root";
    }

    @Override
    public void accept(Visitor vis) {
        // TODO Accepts Visitors
        
    }

    @Override
    public String getDisplayName() {
        return userId;
    }

    /**
     * Creates a Tweet and posts it to current user's personal feed
     * and news feed, as well as updating current user's followers'
     * news feeds.
     * @param message New tweet content.
     */
    public void postTweet(String message) {
        Tweet newTweet = new Tweet(this, message);
        personalFeed.addToFeed(newTweet);

        /** 
         * User can see their own tweet in their newsFeed, so
         * it can also be added there
         */
        newsFeed.addToFeed(newTweet);

        notifyObservers();
    }

    /** 
     * 
     */
    @Override
    public void update(Subject subject) {
        if (subject instanceof User) {
            Tweet newTweet = 
                ((User)subject).newsFeed.getRevChronoTweetList().get(0);
            this.newsFeed.addToFeed(newTweet);
            // TODO Update View
        }
    }

    public String toString() {
        return userId;
    }
}
