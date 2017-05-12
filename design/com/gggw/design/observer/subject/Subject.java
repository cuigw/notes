package com.gggw.design.observer.subject;

import com.gggw.design.observer.observer.Observer;

/**
 * Created by cgw on 2017/3/30.
 */
public interface Subject {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
