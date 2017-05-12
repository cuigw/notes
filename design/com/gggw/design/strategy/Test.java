package com.gggw.design.strategy;

import com.gggw.design.strategy.behavior.impl.QuackSmall;

/**
 * Created by cgw on 2017/3/29.
 */
public class Test {

    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performFly();
        mallard.performQuack();
        mallard.setQuackBehavior(new QuackSmall());
        mallard.performQuack();
    }
}
