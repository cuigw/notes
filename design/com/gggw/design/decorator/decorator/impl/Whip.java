package com.gggw.design.decorator.decorator.impl;

import com.gggw.design.decorator.component.Beverage;
import com.gggw.design.decorator.decorator.CondimentDecorator;

/**
 * 装饰者
 * Created by cgw on 2017/3/31.
 */
public class Whip extends CondimentDecorator {
    //被装饰者
    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + " , Whip";
    }

    @Override
    public double cost() {
        return 0.40 + beverage.cost();
    }
}
