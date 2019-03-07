package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

/**
 * Immunité temporaire à l'étourdissement.
 * @author jeremy
 */
public class BasicImmunToStun extends ImmunityToStun {
    
    /**
     * Constructeur de l'effet d'immunité à l'étourdissement (basique).
     */
    public BasicImmunToStun() {
        super();
        this.duration = 3000;
    }
}
