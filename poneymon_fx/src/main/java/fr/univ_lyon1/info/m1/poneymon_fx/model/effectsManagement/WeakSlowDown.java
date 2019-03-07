package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

/**
 * Sensibilité faible au ralentissement.
 * @author jeremy
 */
public class WeakSlowDown extends SlowDown {
    public WeakSlowDown() {
        this.slowCoeff = 0.75;
    }
}
