package fr.univ_lyon1.info.m1.poneymon_fx.model.ai;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;

import java.io.Serializable;

/**
 * Si un voisin a au moins un demi tour d'avance, on boost.
 * On ne saute pas.
 * @author jeremy
 */
public class NoJumpOptimizedAiStrategy implements AiStrategy, Serializable {
    /**
     * @param model Modèle de poney sur lequel exécuter l'IA.
     */
    public void run(CharacterModel model) {
        for (CharacterModel neighbor : model.getNeighbors()) {
            if (model.distance(neighbor) > 0.5) {
                model.usePower();
            }
        }
    }
}

