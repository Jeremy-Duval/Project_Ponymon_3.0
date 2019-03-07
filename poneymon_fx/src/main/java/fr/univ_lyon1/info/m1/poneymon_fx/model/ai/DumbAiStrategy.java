package fr.univ_lyon1.info.m1.poneymon_fx.model.ai;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;

import java.io.Serializable;

/**
 * Utilise tout les boost dès le départ.
 *
 * @author Victor Doucet doucet.victor@gmail.com
 */
public class DumbAiStrategy implements AiStrategy, Serializable {
    public void run(CharacterModel model) {
        model.usePower();
    }
}
