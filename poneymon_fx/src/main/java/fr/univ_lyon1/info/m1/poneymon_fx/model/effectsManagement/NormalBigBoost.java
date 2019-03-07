package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

/**
 * Gros boost de vitesse d'une durée normale.
 * @author jeremy
 */
public class NormalBigBoost extends Boost {
    /**
     * Constructeur du boost.
     */
    public NormalBigBoost() {
        super();
        this.accelerationCoeff = 2;
        this.duration = 3000;
    }
}
