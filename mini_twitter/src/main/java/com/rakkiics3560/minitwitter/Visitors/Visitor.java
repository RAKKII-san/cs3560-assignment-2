package com.rakkiics3560.minitwitter.visitors;

import com.rakkiics3560.minitwitter.User;

public interface Visitor {
    public void visit(User user);
}
