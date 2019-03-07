package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.BoostEffect;

/**
 * Barrière à placer sur la map.
 * 
 * @author jeremy
 */
public class ObjectBooster extends MapObject {
    
    public ObjectBooster() {
        this.objEffect = new BoostEffect();
    }
}
