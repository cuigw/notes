package com.gggw.design.observer;

import com.gggw.design.observer.observer.impl.CurrentConditionsDisplay;
import com.gggw.design.observer.subject.impl.WeatherData;

/**
 * Created by cgw on 2017/3/30.
 */
public class Test {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);

        weatherData.setMeasurements(80 , 60 ,70);
        weatherData.setMeasurements(11 , 22 ,33);

        currentConditionsDisplay.exitListener(weatherData);

        weatherData.setMeasurements(44 , 55 ,66);
    }
}
