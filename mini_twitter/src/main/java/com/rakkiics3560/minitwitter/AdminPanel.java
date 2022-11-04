/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import java.awt.event.ActionListener;

/**
 * Views all users and groups in a tree view, as well as
 * 
 * @author Rakkii
 */
public class AdminPanel extends javax.swing.JFrame {
    // Static instance
    private static AdminPanel adminInstance = new AdminPanel();
    // Private constructor
    private AdminPanel() {
        if (adminInstance == null) {
            adminInstance = this;
            initComponents();
        }
    }
    
    /**
     * 
     */
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mini Twitter Admin Panel");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
    }
    
    // Static getter
    public static AdminPanel getAdmin() {
        if (adminInstance == null) {
            adminInstance = new AdminPanel();
        }
        return adminInstance;
    }
    
    // create users
    // create user groups

    // Variables declaration - do not modify                     
    private javax.swing.JLabel celsiusLabel;
    private javax.swing.JButton convertButton;
    private javax.swing.JLabel fahrenheitLabel;
    private javax.swing.JTextField tempTextField;
    // End of variables declaration       
}
