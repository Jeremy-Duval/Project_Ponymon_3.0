package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;

import java.io.Serializable;

/**
 * Classe mère des effets de boost temporaire.
 * @author jeremy
 */
public abstract class Boost implements EffectsManagement, Serializable  {
    /**
     * Coefficient d'accélération (>1).
     */
    protected double accelerationCoeff;
    
    protected long startTime;
    /**
     * Temps (en milliseconde) durant lequel l'effet dure.
     */
    protected long duration;
    
    public Boost() {
        this.startTime = -1;
    }
    
    /**
     * Application de l'effet Boost.
     * @param character personnage (model)
     */
    public void effectsManager(CharacterModel character) {
        long currentTime;
        
        if (this.startTime < 0) {
            this.startTime = System.currentTimeMillis();
        }
        
        currentTime = System.currentTimeMillis();
        // Au bout d'un certain temps, l'effet disparait
        if (currentTime >= this.startTime + duration) {
            character.getEffects().remove(EffectStatusEnum.BOOST);
            this.startTime = -1;
        } else {
            character.setStep(character.getStep() * accelerationCoeff);
        }
    }
}
