package com.gggw.design.decorator;

import com.gggw.design.decorator.component.Beverage;
import com.gggw.design.decorator.componentimpl.Blend;
import com.gggw.design.decorator.componentimpl.Espresso;
import com.gggw.design.decorator.decorator.impl.Mocha;
import com.gggw.design.decorator.decorator.impl.Soy;
import com.gggw.design.decorator.decorator.impl.Whip;

/**
 * Created by cgw on 2017/3/31.
 */
public class Test {

    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        display(beverage);
        beverage = new Mocha(beverage);
        display(beverage);
        beverage = new Soy(beverage);
        display(beverage);
        beverage = new Whip(beverage);
        display(beverage);

        beverage = new Blend();
        display(beverage);
    }

    public static void display (Beverage beverage) {
        System.out.println(beverage.getDescription() + " ：  $" + beverage.cost());
    }
}
