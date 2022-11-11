package com.rakkiics3560.minitwitter;

import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** 
 * Generates User's UI, which allows the user to follow other users
 * and post tweets. The UserView also receives tweets from followed
 * users and shares them to the feed.
 * @author Rakkii
 */
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

    /** Builds UI for the User View Panel. */
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

    /** Places buttons onto UI. */
    private void setButtons() {
        followUserButton = new JButton("Follow User");
        postTweetButton = new JButton("Post!");

        add(followUserButton);
        add(postTweetButton);

        followUserButton.setBounds(640, 20, 130, 60);
        postTweetButton.setBounds(640, 300, 130, 60);
    }

    /** Places TextAreas onto UI. */
    private void setTextAreas() {
        followUserTextArea = new JTextArea();
        tweetMessageTextArea = new JTextArea();
        followUserTextArea.setLineWrap(true);
        tweetMessageTextArea.setLineWrap(true);

        add(followUserTextArea);
        add(tweetMessageTextArea);

        followUserTextArea.setBounds(20,20,600,60);
        tweetMessageTextArea.setBounds(20,300,600,60);
    }

    /** Places Lists onto UI. */
    private void setLists() {
        followingListModel = new DefaultListModel<>();
        followingList = new JList<>(followingListModel);
        followingScrollPane = new JScrollPane(followingList);

        newsFeedModel = new DefaultListModel<>();
        newsFeedList = new JList<>(newsFeedModel);
        newsFeedScrollPane = new JScrollPane(newsFeedList);

        add(followingScrollPane);
        add(newsFeedScrollPane);

        updateFollowing();
        updateNewsFeed();
    }

    // This is public because User class needs to use it
    public void updateFollowing() {
        for (User user : userInstance.getFollowingList()) {
            followingListModel.addElement(user.toString());
        }
    }

    // This is public because User class needs to use it
    public void updateNewsFeed() {
        newsFeedModel.removeAllElements();
        for (Tweet tweet : userInstance.getNewsFeed()
                .getRevChronoTweetList()) {
            newsFeedModel.addElement(
                tweet.toString()
            );
        }
    }

    private void followUser() {
        followedUser = followUserTextArea.getText();
        HashMap<String, User> userMap 
                = AdminPanel.getAdmin().getUsers();
        if (userMap.containsKey(followedUser)) {
            User followed = userMap.get(followedUser);
            if (!userInstance.getFollowingList().contains(followed)) {
                userInstance.followUser(userMap.get(followedUser));
                followingListModel.add(
                    0, followedUser
                );
            } else {
                errorMessage = "User already followed.";
                JOptionPane.showMessageDialog(new JFrame(),errorMessage,
                "Follow User Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            errorMessage = "User does not exist.";
            JOptionPane.showMessageDialog(new JFrame(),errorMessage,
                "Follow User Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Posts tweet and notifies a user's followers. */
    private void postTweet() {
        tweetContent = tweetMessageTextArea.getText();
        if (tweetContent.length() > 0) {
            userInstance.postTweet(tweetContent);
            newsFeedModel.add(
                0,
                userInstance.getMostRecentTweet().toString()
            );
        }

        userInstance.notifyObservers();
    }
}
