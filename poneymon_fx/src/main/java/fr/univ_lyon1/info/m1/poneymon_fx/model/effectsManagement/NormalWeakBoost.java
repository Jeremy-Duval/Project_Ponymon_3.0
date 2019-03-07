package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

/**
 * Petit boost de vitesse d'une durée normale.
 * @author jeremy
 */
public class NormalWeakBoost extends Boost {
    /**
     * Constructeur du boost.
     */
    public NormalWeakBoost() {
        super();
        this.accelerationCoeff = 1.5;
        this.duration = 3000;
    }
}
