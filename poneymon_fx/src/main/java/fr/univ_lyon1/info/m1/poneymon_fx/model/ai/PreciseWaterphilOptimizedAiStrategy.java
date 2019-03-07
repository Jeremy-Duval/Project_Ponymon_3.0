package fr.univ_lyon1.info.m1.poneymon_fx.model.ai;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObjectBooster;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObjectPuddle;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.JumpState;

import java.io.Serializable;

/**
 * Si un voisin a au moins un demi tour d'avance, on boost.
 * On saute si l'objet suivant n'est pas de l'eau ou un boost.
 * @author jeremy
 */
public class PreciseWaterphilOptimizedAiStrategy implements AiStrategy, Serializable {
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
                && (!(model.getNextObjectOnMap() instanceof ObjectPuddle))
                && (model.getDistanceBtwObj() <= 0) 
                && (model.getJumpState() == JumpState.noJumping)) {
            model.useJump();
        }
    }
}

