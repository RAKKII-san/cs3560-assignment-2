
package com.rakkiics3560.minitwitter;

/**
 * Main class that constructs the app.
 * @author Rakkii
 */
public class Driver {

    public static void main(String[] args) {
        AdminPanel admin = AdminPanel.getAdmin();
        
        // When run, the program shows up
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                admin.setVisible(true);
            }
        });
    }
}
