package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sets up Users and allows the user to follow other users
 * and get the user's latest tweet.
 * @author RAKKII
 */
public class User extends Subject implements Observer, SysEntry {
    private String userId;
    private List<User> followersList;
    private List<User> followingList;

    private String groupName;
    private Feed personalFeed;
    private Feed newsFeed;
    private long creationTime;
    private long lastUpdateTime;

    private UserView userView;

    public User(String name) {
        userId = name;
        followersList = new ArrayList<>();
        followingList = new ArrayList<>();
        observers = new ArrayList<>();
        personalFeed = new Feed();
        newsFeed = new Feed();
        setAllowsChildren(false);
        groupName = "Root";
        creationTime = System.currentTimeMillis();
        lastUpdateTime = System.currentTimeMillis();
        userView = new UserView(this);
        System.out.println(creationTime);
        System.out.println(lastUpdateTime);
    }

    public void followUser(User user) {
        followingList.add(user);
        user.getFollowersList().add(this);
        newsFeed.mergeFeed(user.getPersonalFeed());
        user.attach(this);
        lastUpdateTime = System.currentTimeMillis();
    }

    // Not needed but could be helpful later
    /* 
    public void unfollowUser(User user) {
        if (followingList.contains(user)) {
            followingList.remove(user);
            user.getFollowersList().remove(this);
            newsFeed.removeUnfollowedUser(user);
            this.detach(user);
        }
    }
    */

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

    public UserView getUserView() {
        return userView;
    }

    public Tweet getMostRecentTweet() {
        return newsFeed.getRevChronoTweetList().get(0);
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public boolean inGroup() {
        return groupName != "Root";
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
        lastUpdateTime = System.currentTimeMillis();
    }

    /** 
     * Updates each follower's feed.
     */
    @Override
    public void update(Subject subject) {
        if (subject instanceof User) {
            Tweet newTweet = 
                ((User)subject).getMostRecentTweet();
            this.getNewsFeed().addToFeed(newTweet);
            this.userView.updateNewsFeed();
        }
    }

    public String toString() {
        return userId;
    }
}
