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

    private DefaultListModel<String> followingListText;
    private DefaultListModel<String> newsFeedText;

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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("User View - " + userInstance.toString());
        setLayout(null);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setVisible(true);

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

        followUserButton.setBounds(640, 20, 140, 60);
        postTweetButton.setBounds(720, 320, 60, 60);
    }

    private void setTextAreas() {
        followUserTextArea = new JTextArea();
        tweetMessageTextArea = new JTextArea();

        add(followUserTextArea);
        add(tweetMessageTextArea);

        followUserTextArea.setBounds(20,20,600,60);
        tweetMessageTextArea.setBounds(20,320,680,60);
    }

    private void setLists() {
        followingListText = new DefaultListModel<>();

        for (User user : userInstance.getFollowingList()) {
            followingListText.addElement(user.toString());
        }

        followingList = new JList<>(followingListText);

        newsFeedText = new DefaultListModel<>();

        for (Tweet tweet : userInstance.getNewsFeed()
                           .getRevChronoTweetList()) {
            StringBuilder sb = new StringBuilder();
            sb.append(tweet.getAuthor());
            sb.append(": ");
            sb.append(tweet.getMessage());
            newsFeedText.addElement(sb.toString());
        }

        newsFeedList = new JList<>(newsFeedText);

        followingScrollPane = new JScrollPane(followingList);
        newsFeedScrollPane = new JScrollPane(newsFeedList);

        add(followingScrollPane);
        add(newsFeedScrollPane);

        followingScrollPane.setBounds(20,100,760,180);
        newsFeedScrollPane.setBounds(20,400,760,180);
    }

    private void followUser() {
        followedUser = followUserTextArea.getText();
        HashMap<String, User> userMap 
                = AdminPanel.getAdmin().getUsers();
        if (userMap.containsKey(followedUser)) {
            userInstance.followUser(userMap.get(followedUser));
            // TODO Refresh following list
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
        }
    }
}
