package fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;

/**
 *  Effet de ralentissement.
 * @author jeremy
 */
public class AquaSlowDownEffect implements ObjectEffect {

    public void applyEffect(CharacterModel character) {
        character.addEffect(EffectStatusEnum.AQUA_SLOW_DOWN);
    }
    
}
