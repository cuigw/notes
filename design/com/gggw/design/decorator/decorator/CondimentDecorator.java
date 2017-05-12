package com.gggw.design.decorator.decorator;

import com.gggw.design.decorator.component.Beverage;

/**
 * 装饰者基类
 * Created by cgw on 2017/3/31.
 */
public abstract class CondimentDecorator extends Beverage {
    public abstract  String getDescription();
}
