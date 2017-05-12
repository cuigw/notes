package com.gggw.design.observer.zzzjdkmodel.observer;



import com.gggw.design.observer.zzzjdkmodel.observable.Weatherdata;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by cgw on 2017/3/30.
 */
public class CurrentConditionsDisplay implements Observer {
    Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionsDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    public void exitListener(Observable observable) {
        observable.deleteObserver(this);

    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof Weatherdata) {
            Weatherdata weatherdata = (Weatherdata) observable;
            this.temperature = weatherdata.getTemperature();
            this.humidity = weatherdata.getHumidity();
            display();
        }
    }

    public void display() {
        System.out.println("Current conditions display:  temperature = " + temperature + "     humidity = " + humidity);
    }
}
