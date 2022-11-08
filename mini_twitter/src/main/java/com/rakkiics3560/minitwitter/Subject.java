package com.rakkiics3560.minitwitter;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Observer class structure.
 * @author Rakkii
 */
public abstract class Subject extends DefaultMutableTreeNode {
    protected List<Observer> observers;

    public void attach(Observer observer) {
        observers.add(observer);
    }
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
