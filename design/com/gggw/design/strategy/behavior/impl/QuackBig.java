package com.gggw.design.strategy.behavior.impl;

import com.gggw.design.strategy.behavior.QuackBehavior;

/**
 * Created by cgw on 2017/3/29.
 */
public class QuackBig implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("我大声的在叫............");
    }
}
