package com.gggw.design.strategy;

import com.gggw.design.strategy.behavior.impl.FlyWithWings;
import com.gggw.design.strategy.behavior.impl.QuackBig;

/**
 * Created by cgw on 2017/3/29.
 */
public class MallardDuck extends Duck {

    public MallardDuck() {
        this.flyBehavior = new FlyWithWings();
        this.quackBehavior = new QuackBig();
    }

    @Override
    public void display() {
        System.out.println("我是一只绿头鸭.............");
    }
}
