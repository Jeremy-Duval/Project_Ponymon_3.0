package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;

import java.io.Serializable;

/**
 *  Classe mère des effets d'étourdissement.
 * @author jeremy
 */
public abstract class Stun implements EffectsManagement, Serializable {
    private long startTime;
    /**
     * Temps (en milliseconde) durant lequel l'effet dure.
     */
    protected long duration;
    
    public Stun() {
        this.startTime = -1;
    }
    
    /**
     * Application de l'effet stun.
     * @param character personnage (model)
     */
    public void effectsManager(CharacterModel character) {
        long currentTime;
        
        if (this.startTime < 0) {
            this.startTime = System.currentTimeMillis();
        }
        
        currentTime = System.currentTimeMillis();
        // Au bout d'un certain temps, l'effet disparait
        if (currentTime >= startTime + duration) {
            character.setPowerIsBlock(false);
            character.getEffects().remove(EffectStatusEnum.STUN);
            character.getEffects().add(EffectStatusEnum.IMMUNITY_TO_STUN);
            this.startTime = -1;
        } else {
            character.setStep(0); 
            character.setPowerIsBlock(true);
        }
    }
}
