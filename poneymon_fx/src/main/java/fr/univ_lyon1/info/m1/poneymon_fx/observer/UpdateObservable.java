package fr.univ_lyon1.info.m1.poneymon_fx.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public abstract class UpdateObservable {
    private final List<UpdateObserver> observers = new ArrayList<UpdateObserver>();

    public void addObserver(UpdateObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Notifie les observeurs quand les données sont modifiées.
     */
    public void notifyObservers() {
        for (UpdateObserver observer : this.observers) {
            observer.notifyUpdate(this);
        }
    }
}
