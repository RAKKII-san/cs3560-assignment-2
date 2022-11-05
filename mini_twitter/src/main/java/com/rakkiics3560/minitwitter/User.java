/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import java.util.List;

/**
 * Basic functions checklist #2, #4, #5
 * @author RAKKII
 */
public class User {

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
    private Feed newsFeed;

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

    // make tweet
}
