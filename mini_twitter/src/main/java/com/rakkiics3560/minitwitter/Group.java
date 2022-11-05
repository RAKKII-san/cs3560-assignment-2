/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAKKII
 */
public class Group implements SysEntry {
    // unique id is determined by counter
    private static int groupCounter = 0;
    private int groupId;
    private String groupName;
    private List<User> userList;
    private List<Group> groupList; // recursive

    // Group can be instantiated by any number of users
    public Group(User... users) {
        userList = new ArrayList<>();
        groupList = new ArrayList<>();
        for (User user : users) {
            userList.add(user);
        }
        groupId = groupCounter;
        groupCounter++;
    }

    // Established preset name included in instantiation
    public Group(String name, User... users) {
        userList = new ArrayList<>();
        groupList = new ArrayList<>();
        for (User user : users) {
            userList.add(user);
        }
        groupName = name;
        groupId = groupCounter;
        groupCounter++;
    }

    public void addMember(User user) {
        if (!userList.contains(user) && user.getGroupList().size() == 0) {
            userList.add(user);
        }
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String name) {
        groupName = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    @Override
    public void accept() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void displayName() {
        // TODO Auto-generated method stub
        
    }

}
