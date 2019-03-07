package fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;

import java.io.Serializable;

/**
 * Classe mère des effets d'immunité temporaire.
 * @author jeremy
 */
public abstract class ImmunityToStun implements EffectsManagement, Serializable {
    protected long startTime;
    /**
     * Temps (en milliseconde) durant lequel l'effet dure.
     */
    protected long duration;
    
    public ImmunityToStun() {
        this.startTime = -1;
    }
    
    /**
     * Application de l'effet ImmunityToStun.
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
            character.getEffects().remove(EffectStatusEnum.IMMUNITY_TO_STUN);
            this.startTime = -1;
        } else {
            character.getEffects().remove(EffectStatusEnum.STUN);
        }
    }
}
