package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.AquaSlowDownEffect;

/**
 * Flaque d'eau à placer sur la map.
 * 
 * @author jeremy
 */
public class ObjectPuddle extends MapObject {
    public ObjectPuddle() {
        this.objEffect = new AquaSlowDownEffect();
    }
}
