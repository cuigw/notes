package com.gggw.design.decorator.component;

/**
 * 组件
 * Created by cgw on 2017/3/31.
 */
public abstract class Beverage {

    public String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract  double cost();
}
