package fr.univ_lyon1.info.m1.poneymon_fx.model.ai;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObjectBooster;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.JumpState;

import java.io.Serializable;

/**
 * Si un voisin a au moins un demi tour d'avance, on boost.
 * On saute si l'objet suivant n'est pas un boost.
 */
public class SimpleAiStrategy implements AiStrategy, Serializable {
    /**
     * @param model Modèle de poney sur lequel exécuter l'IA.
     */
    public void run(CharacterModel model) {
        for (CharacterModel neighbor : model.getNeighbors()) {
            if (model.distance(neighbor) > 0.5) {
                model.usePower();
            }
        }
        
        if ((model.getNextObjectOnMap() != null) 
                && (!(model.getNextObjectOnMap() instanceof ObjectBooster))
                && (model.getDistanceBtwObj() <= model.getStep() / 2) 
                && (model.getJumpState() == JumpState.noJumping)) {
            model.useJump();
        }
    }
}
