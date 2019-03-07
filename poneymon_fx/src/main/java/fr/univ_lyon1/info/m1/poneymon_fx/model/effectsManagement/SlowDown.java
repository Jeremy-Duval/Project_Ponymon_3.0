package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;

import java.io.Serializable;

/**
 * Classe mère des effets de ralentissement.
 * @author jeremy
 */
public abstract class SlowDown implements EffectsManagement, Serializable {
    /**
     * Coefficient de relentissement : entre 0 et 1.
     */
    protected double slowCoeff;
    
    public void effectsManager(CharacterModel character) {
        character.setStep(character.getStep() * slowCoeff);
    }
}
