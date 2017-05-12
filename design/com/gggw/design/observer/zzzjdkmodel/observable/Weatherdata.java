package com.gggw.design.observer.zzzjdkmodel.observable;

import java.util.Observable;

/**
 * Created by cgw on 2017/3/30.
 */
public class Weatherdata extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;

    public Weatherdata() {}

    public void measurementsChanged() {
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
