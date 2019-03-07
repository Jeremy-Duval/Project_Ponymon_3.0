package fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;

/**
 * Effet étoudit.
 * @author jeremy
 */
public class StunEffect implements ObjectEffect {

    public void applyEffect(CharacterModel character) {
        character.addEffect(EffectStatusEnum.STUN);
    }
}
