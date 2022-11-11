package com.rakkiics3560.minitwitter;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.regex.Pattern;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.WindowConstants;

/**
 * Views all users and groups in a tree view,
 * adds users and groups, counts number of users, groups, and tweets,
 * opens user views, and calculates percentage of tweets 
 * with positive words.
 * Entrance to the program.
 * @author Rakkii
 */
public class AdminPanel extends JFrame {            
    private static AdminPanel adminInstance;

    protected static HashMap<String, User> users;
    protected static HashMap<String, Group> groups;

    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode userViewSelection;

    private JFrame popUpFrame;
    private JTree tree;
    private JScrollPane treeScrollPane;
    private JTextField addUserTextArea;
    private JTextField addGroupTextArea;

    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton countGroupsButton;
    private JButton countTweetsButton;
    private JButton countUsersButton;
    private JButton openUserViewButton;
    private JButton percentPositiveButton;

    private String newUserName;
    private String newGroupName;
    private String errorMessage;


    private static Pattern alphPattern = Pattern.compile(
        "^[a-zA-Z0-9]*$"
    );

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600; 

    /** Private constructor */
    private AdminPanel() {
        if (adminInstance == null) {
            adminInstance = this;
            users = new HashMap<>();
            groups = new HashMap<>();
            initComponents();
        }
    }
    
    /** Builds UI for the Admin Panel. */
    private void initComponents() {
        setButtons();
        setTree();
        setTextArea();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mini Twitter Admin Panel");
        setLayout(null);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setVisible(true);
        
        // Button function on click
        countUsersButton.addActionListener(e -> {
            countUsers();
        });

        countGroupsButton.addActionListener(e -> {
            countGroups();
        });


        addUserButton.addActionListener(e -> {
            newUserName = addUserTextArea.getText();
            if (isAlphaNumeric(newUserName)) {
                addUser(newUserName);
            } else {
                errorMessage = "Username must be a non-empty, "
                    + "alphanumeric string.";
                JOptionPane.showMessageDialog(
                    new JFrame(), errorMessage, 
                    "Username Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        addGroupButton.addActionListener(e -> {
            newGroupName = addGroupTextArea.getText();
            if (isAlphaNumeric(newGroupName)) {
                addGroup(newGroupName);
            } else {
                errorMessage = "Group name must be a non-empty, "
                    + "alphanumeric string.";
                JOptionPane.showMessageDialog(
                    new JFrame(), errorMessage, 
                    "Group Name Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        openUserViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                userViewSelection = 
                    (DefaultMutableTreeNode)tree
                        .getLastSelectedPathComponent();
                if (userViewSelection != null 
                        && !userViewSelection.getAllowsChildren()) {
                    ((User)userViewSelection).getUserView()
                        .setVisible(true);
                } else {
                    errorMessage = "Invalid selection.\n"
                    + "Please select a user.";
                    JOptionPane.showMessageDialog(
                        new JFrame(), errorMessage, 
                        "User View Error", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        countTweetsButton.addActionListener(e -> {
            countTweets();
        });

        percentPositiveButton.addActionListener(e -> {
            double posPercent = calculatePositivePercent();
            String posPercentFormat = String.format(
                "%.1f", posPercent
            );
            
            if (Double.isNaN(posPercent)) {
                JOptionPane.showMessageDialog(
                    popUpFrame, "There are no tweets, so positive " 
                            + "tweet % cannot be calculated."
                );
            } else {
                JOptionPane.showMessageDialog(
                    popUpFrame, "Positive tweets percent: " 
                        + posPercentFormat + '%'
                );
            }
        });
    }
    
    private double calculatePositivePercent() {
        Collection<User> userSet = users.values();
        Collection<Tweet> tweetSet = new HashSet<Tweet>();
        PositiveTweetVisitor visitor = new PositiveTweetVisitor();

        double posCount = 0;

        for (User user : userSet) {
            tweetSet.addAll(
                user.getPersonalFeed().getTweetMap().values()
            );
        }

        for (Tweet tweet : tweetSet) {
            if (tweet.accept(visitor)) {
                posCount++;
            }
        }

        posCount /= tweetSet.size();
        posCount *= 100;

        return posCount;
	}

	private void countTweets() {
        int tweetCount = 0;
            for (User user : users.values()) {
                tweetCount += user.getPersonalFeed()
                                  .getTweetMap().size();
            }
        JOptionPane.showMessageDialog(
            popUpFrame, "Total Number of Tweets: " + tweetCount
        );
	}

	private void countUsers() {
        JOptionPane.showMessageDialog(
            popUpFrame, "Total Number of Users: " + users.size()
        );
	}

    private void countGroups() {
        JOptionPane.showMessageDialog(
            popUpFrame, "Total Number of Groups: " + groups.size()
        );
    }

	/** Places buttons onto UI. */
    private void setButtons() {
        addUserButton = new JButton("Add User");
        addGroupButton = new JButton("Add Group");
        countUsersButton = new JButton("Count Users");
        countGroupsButton = new JButton("Count Groups");
        countTweetsButton = new JButton("Count Tweets");
        openUserViewButton = new JButton("Open User");
        percentPositiveButton = new JButton("Show Positive %");

        add(addUserButton);
        add(addGroupButton);
        add(openUserViewButton);
        add(countUsersButton);
        add(countGroupsButton);
        add(countTweetsButton);
        add(percentPositiveButton);

        addUserButton.setBounds(665, 20, 100, 60);
        addGroupButton.setBounds(665, 100, 100, 60);
        openUserViewButton.setBounds(405, 180, 360, 60);
        countUsersButton.setBounds(405, 400, 170, 60);
        countGroupsButton.setBounds(595, 400, 170, 60);
        countTweetsButton.setBounds(405, 480, 170, 60);
        percentPositiveButton.setBounds(595, 480, 170, 60);
    }

    /** Places TreeView onto UI. */
    private void setTree() {
        Group rootGroup = new Group("Root");
        groups.put("Root", rootGroup);
        root = new DefaultMutableTreeNode(rootGroup);
        tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeScrollPane = new JScrollPane(tree);
        add(treeScrollPane);
        treeScrollPane.setBounds(20,20,365,520);
        
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
			@Override
			public Component getTreeCellRendererComponent(JTree tr, Object value, boolean isSelected, boolean expanded, boolean isLeaf, int row, boolean hasFocus) {
				Component result = getTreeCellRendererComponent(tr, value, isSelected, expanded, isLeaf, row, hasFocus);

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

				if (node instanceof Group) {
					this.setIcon(UIManager.getIcon("FileView.directoryIcon"));
				}

				if (node instanceof User) {
					this.setIcon(UIManager.getIcon("FileView.fileIcon"));
				}

				return result;
			}
		});
    }

    /** Places TextAreas onto UI. */
    private void setTextArea() {
        addUserTextArea = new JTextField();
        addGroupTextArea = new JTextField();

        add(addUserTextArea);
        add(addGroupTextArea);

        addUserTextArea.setBounds(405,20,240,60);
        addGroupTextArea.setBounds(405,100,240,60);
    }

    private boolean isAlphaNumeric(String s) {
        return s.length() > 0 && alphPattern.matcher(s).find();
    }

    /** Static getter */
    public static AdminPanel getAdmin() {
        if (adminInstance == null) {
            adminInstance = new AdminPanel();
        }
        return adminInstance;
    }
    
    /** Create user */
    private void addUser(String name) {
        name = name.toLowerCase();
        if (!users.containsKey(name)) {
            User newUser = new User(name);
            users.put(name, newUser);

            // add node to tree
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

            // if no location selected
            if (node == null) {
                root.add(newUser);
                groups.get("Root").addMember(newUser);
            } 
            else {
                if (node.getAllowsChildren()) { // Groups allow children
                    node.add(newUser);
                    groups.get(node.toString()).addMember(newUser);
                } else { // Users do not allow children
                    DefaultMutableTreeNode parent =
                        (DefaultMutableTreeNode)node.getParent();
                    parent.add(newUser);
                }
            }
            updateTree();
        } else {
            errorMessage = "Username already exists.";
            JOptionPane.showMessageDialog(
                new JFrame(), errorMessage, 
                "Username Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /** create user groups */
    private void addGroup(String name) {
        name = name.toUpperCase();
        if (!groups.containsKey(name)) {
            Group newGroup = new Group(name);
            groups.put(name, newGroup);
            // add node to tree
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            // if no location selected
            if (node == null) {
                root.add(newGroup);
            } 
            else {
                if (node.getAllowsChildren()) { // Groups allow children
                    node.add(newGroup);
                } else { // Users do not allow children
                    DefaultMutableTreeNode parent =
                        (DefaultMutableTreeNode)node.getParent();
                    parent.add(newGroup);
                }
            }
            updateTree(); 
        } else {
            errorMessage = "Group name already exists.";
            JOptionPane.showMessageDialog(
                new JFrame(), errorMessage, 
                "Group Name Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateTree() {
        ((DefaultTreeModel) tree.getModel()).
                nodesWereInserted(root, new int[]{root.getChildCount() - 1});
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }
}