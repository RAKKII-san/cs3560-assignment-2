/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rakkiics3560.minitwitter;

import com.rakkiics3560.minitwitter.visitors.Visitor;

/**
 *
 * @author Rakkii
 */
public interface Visitable {
    public void accept(Visitor vis);
}
