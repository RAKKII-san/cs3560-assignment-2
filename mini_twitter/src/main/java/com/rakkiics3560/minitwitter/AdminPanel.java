/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import java.awt.event.ActionListener;
import java.util.List;

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

    // Private constructor
    private AdminPanel() {
        if (adminInstance == null) {
            adminInstance = this;
            initComponents();
        }
    }
    
    /** Builds UI for the Admin Panel. */
    private void initComponents() {
        countUserButton = new JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mini Twitter Admin Panel");
        
        countUserButton.setText("Count Users");
        countUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(countUserButton)
                    )
                )
                .addContainerGap()
            )
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {countUserButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(countUserButton)
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
        // might need a button for this?
    // create user groups
        // also might need a button for this?
    // make root group
        // fine to hardcode??? idk

    // Variables declaration               
    private javax.swing.JButton countUserButton;

    // End of variables declaration       
}
