package com.rakkiics3560.minitwitter;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.regex.Pattern;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

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
    protected static PriorityQueue<User> lastUpdatedUsers = 
            new PriorityQueue<>(new UserUpdateComparator());

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
    private JButton validateButton;
    private JButton checkLastUpdatedUserButton;

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
        setCustomIcons();
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

        validateButton.addActionListener(e -> {
            HashSet<String> nameSet = new HashSet<>();
            for (String name : users.keySet()) {
                nameSet.add(name);
            }
            for (String name : groups.keySet()) {
                nameSet.add(name);
            }
            if (nameSet.size() == users.size() + groups.size()) {
                JOptionPane.showMessageDialog(
                    popUpFrame, "All users and groups are valid."
                );
            } else {
                JOptionPane.showMessageDialog(
                    popUpFrame, 
                    "There are at least two invalid user/group names."
                );
            }
        });

        checkLastUpdatedUserButton.addActionListener(e -> {
            if (users.size() == 0) {
                JOptionPane.showMessageDialog(
                    popUpFrame, "There are no users."
                );
            } else {
                String lastUpdatedUserName = 
                       lastUpdatedUsers.peek().getName();
                JOptionPane.showMessageDialog(
                    popUpFrame, 
                    "Last Updated User: " + lastUpdatedUserName
                );
            }
        });
    }
    
    /**
     * Calculates percentage of tweets with positive words
     * using Visitor pattern.
     * @return % of tweets with positive words
     */
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
        validateButton = new JButton("Validate");
        checkLastUpdatedUserButton = new JButton("Last Updated User");

        add(addUserButton);
        add(addGroupButton);
        add(openUserViewButton);
        add(countUsersButton);
        add(countGroupsButton);
        add(countTweetsButton);
        add(percentPositiveButton);
        add(validateButton);
        add(checkLastUpdatedUserButton);

        addUserButton.setBounds(665, 20, 100, 60);
        addGroupButton.setBounds(665, 100, 100, 60);
        openUserViewButton.setBounds(405, 180, 360, 60);
        countUsersButton.setBounds(405, 400, 170, 60);
        countGroupsButton.setBounds(595, 400, 170, 60);
        countTweetsButton.setBounds(405, 480, 170, 60);
        percentPositiveButton.setBounds(595, 480, 170, 60);
        validateButton.setBounds(405, 320, 170, 60);
        checkLastUpdatedUserButton.setBounds(595, 320, 170, 60);
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

    /** 
     * Sets icons for users and groups. 
     */
    private void setCustomIcons() {
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
			@Override
			public Component getTreeCellRendererComponent(
                JTree tr, Object value, 
                boolean isSelected, boolean expanded, 
                boolean isLeaf, int row, boolean hasFocus
            ) {
				Component result = super.getTreeCellRendererComponent(
                    tr, value, isSelected, 
                    expanded, isLeaf, row, hasFocus);

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

				if (node instanceof Group) {
					this.setIcon(UIManager.getIcon(
                        "FileView.directoryIcon"
                    ));
				}

				if (node instanceof User) {
					this.setIcon(UIManager.getIcon(
                        "FileView.fileIcon"
                    ));
				}

				return result;
			}
		});
    }

    /**
     * Checks string if it only contains alphanumeric characters.
     * @param s String to be tested.
     * @return if string is not empty and only contains alphanumeric chars.
     */
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
    
    /** Create user. */
    private void addUser(String name) {
        if (!users.containsKey(name) && !groups.containsKey(name)) {
            User newUser = new User(name);
            users.put(name, newUser);

            // add node to tree
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

            // if no location selected
            if (node == null) {
                root.add(newUser);
                groups.get("Root").addMember(newUser);
                updateTree(root);
            } 
            else {
                if (node.getAllowsChildren()) { // Groups allow children
                    node.add(newUser);
                    groups.get(node.toString()).addMember(newUser);
                    updateTree(node);
                } else { // Users do not allow children
                    DefaultMutableTreeNode parent =
                        (DefaultMutableTreeNode)node.getParent();
                    parent.add(newUser);
                    groups.get(parent.toString()).addMember(newUser);
                    updateTree(parent);
                }
            }
        } else {
            errorMessage = "ID already exists.";
            JOptionPane.showMessageDialog(
                new JFrame(), errorMessage, 
                "Username Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /** Create user groups. */
    private void addGroup(String name) {
        if (!groups.containsKey(name) && !users.containsKey(name)) {
            Group newGroup = new Group(name);
            groups.put(name, newGroup);
            // add node to tree
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            // if no location selected
            if (node == null) {
                root.add(newGroup);
                groups.get("Root").addSubgroup(newGroup);
                updateTree(root); 
            } 
            else {
                if (node.getAllowsChildren()) { // Groups allow children
                    node.add(newGroup);
                    groups.get(node.toString()).addSubgroup(newGroup);
                    updateTree(node); 
                } else { // Users do not allow children
                    DefaultMutableTreeNode parent =
                        (DefaultMutableTreeNode)node.getParent();
                    parent.add(newGroup);
                    groups.get(parent.toString()).addSubgroup(newGroup);
                    updateTree(parent); 
                }
            }

        } else {
            errorMessage = "ID name already exists.";
            JOptionPane.showMessageDialog(
                new JFrame(), errorMessage, 
                "Group Name Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateTree(DefaultMutableTreeNode node) {
        ((DefaultTreeModel) tree.getModel()).
                nodesWereInserted(node, new int[]{node.getChildCount() - 1});
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public PriorityQueue<User> getUserPriorityQueue() {
        return lastUpdatedUsers;
    }
}

/** Used for the Most Recently Updated User comparison. */
class UserUpdateComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if (o1.getLastUpdateTime() < o2.getLastUpdateTime()) {
            return 1;
        } else if (o1.getLastUpdateTime() > o2.getLastUpdateTime()) {
            return -1;
        }
        return 0;
    }
}