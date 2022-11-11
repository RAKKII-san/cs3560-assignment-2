/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Sets up Groups and stores the User members of said Group.
 * @author Rakkii
 */
public class Group extends DefaultMutableTreeNode implements SysEntry {
    private String groupName;
    private List<User> userList;
    private List<Group> groupList; // recursive

    // Established group name included in instantiation
    // Group can be instantiated by any number of users
    public Group(String name, User... users) {
        userList = new ArrayList<>();
        groupList = new ArrayList<>();
        for (User user : users) {
            userList.add(user);
        }
        setAllowsChildren(true);
        groupName = name;
    }

    public void addMember(User user) {
        if (!userList.contains(user) && !user.inGroup()) {
            userList.add(user);
        }
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

    public String toString() {
        return groupName;
    }
}
