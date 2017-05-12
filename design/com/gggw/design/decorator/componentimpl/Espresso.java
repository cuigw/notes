package com.gggw.design.decorator.componentimpl;

import com.gggw.design.decorator.component.Beverage;

/**
 * 浓咖啡 （组件，被装饰者）
 * Created by cgw on 2017/3/31.
 */
public class Espresso extends Beverage {

    public Espresso() {
        description = "Espresso";
    }

    @Override
    public double cost() {
        return 3.00;
    }
}
