package com.gggw.design.observer.observer.impl;

import com.gggw.design.observer.display.DisplayElement;
import com.gggw.design.observer.observer.Observer;
import com.gggw.design.observer.subject.Subject;

/**
 * Created by cgw on 2017/3/30.
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private Subject subject;

    public CurrentConditionsDisplay(Subject subject) {
        this.subject = subject;
        subject.registerObserver(this);
    }

    public void exitListener(Subject subject) {
        subject.removeObserver(this);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        System.out.println("Current conditions display:  temperature = " + temperature + "     humidity = " + humidity);
    }
}
