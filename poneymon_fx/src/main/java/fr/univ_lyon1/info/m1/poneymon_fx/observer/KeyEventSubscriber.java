package fr.univ_lyon1.info.m1.poneymon_fx.observer;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public interface KeyEventSubscriber {
    /**
     * Appelé quand une touche est pressée.
     * @param s Touche pressée
     */
    void keyPressed(String s);
}
