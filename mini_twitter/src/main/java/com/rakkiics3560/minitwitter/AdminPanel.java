package com.rakkiics3560.minitwitter;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.WindowConstants;


/**
 * Views all users and groups in a tree view, as well as
 * 
 * @author Rakkii
 */
public class AdminPanel extends javax.swing.JFrame {
    // Static instance
    private static AdminPanel adminInstance = new AdminPanel();

    protected static List<User> users;
    protected static List<Group> groups;

    private DefaultMutableTreeNode root = new DefaultMutableTreeNode();

    private JTree tree;

    private static JFrame frame;

    // Private constructor
    private AdminPanel() {
        if (adminInstance == null) {
            adminInstance = this;
            users = new ArrayList<>();
            groups = new ArrayList<>();
            tree = new JTree();

            groups.add(new Group("Root"));
            initComponents();
        }
    }
    
    /** Builds UI for the Admin Panel. */
    private void initComponents() {
        countUsersButton = new JButton();
        countGroupsButton = new JButton();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mini Twitter Admin Panel");
        
        countUsersButton.setText("Count Users");
        countUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(frame, "Total Number of Users: " + users.size());
            }
        });

        countGroupsButton.setText("Count Groups");
        countGroupsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(frame, "Total Number of Groups: " + groups.size());
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(
                    GroupLayout.Alignment.LEADING
                )
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(countUsersButton)
                        .addComponent(countGroupsButton)
                    )
                )
                .addContainerGap()
            )
        );

        layout.linkSize(
            SwingConstants.HORIZONTAL, 
            new Component[] {countUsersButton, countGroupsButton}
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(
                    GroupLayout.Alignment.LEADING
                )
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(countUsersButton)
                    )
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(countGroupsButton)
                    )
                )
                .addContainerGap()
            )
        );

        pack();
    }
    
    // Static getter
    public static AdminPanel getAdmin() {
        if (adminInstance == null) {
            adminInstance = new AdminPanel();
        }
        return adminInstance;
    }
    
    // create users
    public void addUser() {
        
    }
    // create user groups
        // also might need a button for this?
    // make root group
        // fine to hardcode??? idk

    // Variables declaration               
    private JButton countUsersButton;
    private JButton countGroupsButton;
    private JButton countTweetsButton;
    private JButton percentPositiveButton;
    private JButton addUserButton;
    private JButton addGroupButton;

    // End of variables declaration       
}
