/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic functions checklist #2, #4, #5
 * @author RAKKII
 */
public class User extends Subject implements Observer, SysEntry {
    private String userId;
    private List<User> followersList;
    private List<User> followingList;

    /** 
     * group list allows for future extending for a single user
     * being in multiple groups instead of a single group
     * ...think how twitter allows a user to be in multiple lists...
     * so using a boolean to represent whether the user
     * is in a group or not isn't a good idea
     * but for the assignment's sake
     * a single user can only be in one group
     */
    private List<Group> groupList;
    private Feed personalFeed;
    private Feed newsFeed;

    public User(String name) {
        userId = name;
        followersList = new ArrayList<>();
        followingList = new ArrayList<>();
        personalFeed = new Feed();
        newsFeed = new Feed();
    }

    public void followUser(User user) {
        followingList.add(user);
        user.getFollowersList().add(this);
    }

    public void unfollowUser(User user) {
        if (followingList.contains(user)) {
            followingList.remove(user);
            user.getFollowersList().remove(this);
        }
    }

    public List<User> getFollowersList() {
        return followersList;
    }

    public List<User> getFollowingList() {
        return followingList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public Feed getPersonalFeed() {
        return personalFeed;
    }

    public Feed getNewsFeed() {
        return newsFeed;
    }

    @Override
    public void accept() {
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
        newsFeed.addToFeed(newTweet);

        // TODO update followers' feeds as well!
        // TODO Observers?
    }
}
