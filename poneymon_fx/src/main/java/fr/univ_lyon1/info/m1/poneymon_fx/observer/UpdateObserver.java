package fr.univ_lyon1.info.m1.poneymon_fx.observer;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public interface UpdateObserver {
    /**
     * Est appelé par l'observable lors d'une mise à jour.
     */
    void notifyUpdate(UpdateObservable observable);
}
