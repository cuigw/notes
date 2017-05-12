package com.gggw.design.observer.zzzjdkmodel;

import com.gggw.design.observer.zzzjdkmodel.observable.Weatherdata;
import com.gggw.design.observer.zzzjdkmodel.observer.CurrentConditionsDisplay;

/**
 * Created by cgw on 2017/3/30.
 */
public class Test {
    public static void main(String[] args) {
        Weatherdata weatherdata = new Weatherdata();
        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherdata);

        weatherdata.setMeasurements(80 , 60 ,70);
        weatherdata.setMeasurements(11 , 22 ,33);

        currentConditionsDisplay.exitListener(weatherdata);
        weatherdata.setMeasurements(11 , 22 ,33);
    }
}
