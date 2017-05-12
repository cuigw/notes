package com.gggw.design.decorator.componentimpl;

import com.gggw.design.decorator.component.Beverage;

/**
 * 混合咖啡
 * Created by cgw on 2017/3/31.
 */
public class Blend extends Beverage {

    public Blend() {
        description = "Blend coffee";
    }

    @Override
    public double cost() {
        return 4.00;
    }
}
