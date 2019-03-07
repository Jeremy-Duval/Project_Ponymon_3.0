package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.ObjectEffect;
import java.io.Serializable;

/**
 * Classe mère de tout les objets d'une map.
 * @author jeremy
 */
public abstract class MapObject implements Serializable {
    protected ObjectEffect objEffect;
    
    public MapObject() {
        this.objEffect = null;
    }
}
