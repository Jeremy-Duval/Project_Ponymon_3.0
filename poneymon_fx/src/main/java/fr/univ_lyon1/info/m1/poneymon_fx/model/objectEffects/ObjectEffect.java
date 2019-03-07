package fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import java.io.Serializable;

/**
 * Interface à implémenter pour la création d'effet pour les objets de la map.
 * @author jeremy
 */
public interface ObjectEffect extends Serializable {
    void applyEffect(CharacterModel character);
}
