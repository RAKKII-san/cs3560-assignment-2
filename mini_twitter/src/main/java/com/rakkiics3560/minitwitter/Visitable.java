package com.rakkiics3560.minitwitter;

/**
 * Visitable objects structure, part of Visitors pattern.
 * @author Rakkii
 */
public interface Visitable {
    public boolean accept(Visitor vis);
}
