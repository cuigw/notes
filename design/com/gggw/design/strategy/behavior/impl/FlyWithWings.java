package com.gggw.design.strategy.behavior.impl;

import com.gggw.design.strategy.behavior.FlyBehavior;

/**
 * Created by cgw on 2017/3/29.
 */
public class FlyWithWings implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("我正在飞呢.............");
    }
}
