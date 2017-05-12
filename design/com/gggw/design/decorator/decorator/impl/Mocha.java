package com.gggw.design.decorator.decorator.impl;

import com.gggw.design.decorator.component.Beverage;
import com.gggw.design.decorator.decorator.CondimentDecorator;

/**
 * 装饰者
 * Created by cgw on 2017/3/31.
 */
public class Mocha extends CondimentDecorator {
    //被装饰者
    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + " , Mocha";
    }

    @Override
    public double cost() {
        return 0.20 + beverage.cost();
    }
}
