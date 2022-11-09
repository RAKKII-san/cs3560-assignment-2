/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

import com.rakkiics3560.minitwitter.visitors.Visitor;

/**
 *
 * @author Rakkii
 */
public class Group extends DefaultMutableTreeNode implements SysEntry {
    private String groupName;
    private final String ICON_FILENAME = "/icons/groupIcon.png";
    private List<User> userList;


    // Established group name included in instantiation
    // Group can be instantiated by any number of users
    public Group(String name, User... users) {
        userList = new ArrayList<>();
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

    @Override
    public void accept(Visitor vis) {
        // TODO Visitors?
        
    }

    public String toString() {
        return groupName;
    }

    @Override
    public String getIconString() {
        return ICON_FILENAME;
    }
}
