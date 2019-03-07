package fr.univ_lyon1.info.m1.poneymon_fx.model.ai;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObjectBooster;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.JumpState;

import java.io.Serializable;

/**
 * Utilise tout les boost dès le départ.
 * @author jeremy
 */
public class SemiDumbAiStrategy implements AiStrategy, Serializable {
    /**
     * @param model Modèle de poney sur lequel exécuter l'IA.
     */
    public void run(CharacterModel model) {
        model.usePower();
        
        if ((model.getNextObjectOnMap() != null) 
                && (!(model.getNextObjectOnMap() instanceof ObjectBooster))
                && (model.getDistanceBtwObj() <= 0) 
                && (model.getJumpState() == JumpState.noJumping)) {
            model.useJump();
        }
    }
}
