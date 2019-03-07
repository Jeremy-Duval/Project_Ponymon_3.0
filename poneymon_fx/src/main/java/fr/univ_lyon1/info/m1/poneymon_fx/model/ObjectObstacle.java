package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.StunEffect;

/**
 * Barrière à placer sur la map.
 * 
 * @author jeremy
 */
public class ObjectObstacle extends MapObject {
    
    public ObjectObstacle() {
        this.objEffect = new StunEffect();
    }
}
