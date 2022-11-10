package com.rakkiics3560.minitwitter;

import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class UserView extends JFrame {
    private User userInstance;

    private JTextArea followUserTextArea;
    private JTextArea tweetMessageTextArea;
    
    private JButton followUserButton;
    private JButton postTweetButton;

    private JList<String> followingList;
    private JList<String> newsFeedList;

    private DefaultListModel<String> followingListModel;
    private DefaultListModel<String> newsFeedModel;

    private JScrollPane followingScrollPane;
    private JScrollPane newsFeedScrollPane;

    private String followedUser;
    private String tweetContent;
    private String errorMessage;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    public UserView(User user) {
        userInstance = user;
        initComponents();
    }

    private void initComponents() {
        setButtons();
        setTextAreas();
        setLists();
        setTitle("User View - " + userInstance.toString());
        setLayout(null);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        followingScrollPane.setBounds(20,100,750,180);
        newsFeedScrollPane.setBounds(20,380,750,160);

        // Button function on click
        followUserButton.addActionListener(e -> {
            followUser();
        });

        postTweetButton.addActionListener(e -> {
            postTweet();
        });
    }

    private void setButtons() {
        followUserButton = new JButton("Follow User");
        postTweetButton = new JButton("Post!");

        add(followUserButton);
        add(postTweetButton);

        followUserButton.setBounds(640, 20, 130, 60);
        postTweetButton.setBounds(640, 300, 130, 60);
    }

    private void setTextAreas() {
        followUserTextArea = new JTextArea();
        tweetMessageTextArea = new JTextArea();

        add(followUserTextArea);
        add(tweetMessageTextArea);

        followUserTextArea.setBounds(20,20,600,60);
        tweetMessageTextArea.setBounds(20,300,600,60);
    }

    private void setLists() {
        updateFollowers();
        updateNewsFeed();
    }

    private void updateFollowers() {
        followingListModel = new DefaultListModel<>();

        for (User user : userInstance.getFollowingList()) {
            followingListModel.addElement(user.toString());
        }

        followingList = new JList<>(followingListModel);
        followingScrollPane = new JScrollPane(followingList);
        add(followingScrollPane);
    }

    public void updateNewsFeed() {
        newsFeedModel = new DefaultListModel<>();

        for (Tweet tweet : userInstance.getNewsFeed()
                .getRevChronoTweetList()) {
            StringBuilder sb = new StringBuilder();
            sb.append(tweet.getAuthor());
            sb.append(": ");
            sb.append(tweet.getMessage());
            newsFeedModel.addElement(sb.toString());
        }

        newsFeedList = new JList<>(newsFeedModel);
        newsFeedScrollPane = new JScrollPane(newsFeedList);
        add(newsFeedScrollPane);
    }

    private void followUser() {
        followedUser = followUserTextArea.getText();
        HashMap<String, User> userMap 
                = AdminPanel.getAdmin().getUsers();
        if (userMap.containsKey(followedUser)) {
            userInstance.followUser(userMap.get(followedUser));
            followingListModel.add(0, followedUser);
            updateNewsFeed();
        } else {
            errorMessage = "User does not exist.";
            JOptionPane.showMessageDialog(new JFrame(),errorMessage,
                "Follow User Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postTweet() {
        tweetContent = tweetMessageTextArea.getText();
        if (tweetContent.length() > 0) {
            userInstance.postTweet(tweetContent);
            /* 
            StringBuilder sb = new StringBuilder();
            sb.append(userInstance.toString());
            sb.append(": ");
            sb.append(tweetContent);
            newsFeedModel.add(0, tweetContent);
            */
            updateNewsFeed();
        }

        userInstance.notifyObservers();
    }
}
