package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info;

import fr.univ_lyon1.info.m1.poneymon_fx.model.Map;

import java.io.Serializable;

public class MapInfo implements Serializable {
    private Map map;
    private boolean quit;

    public MapInfo(Map map, boolean quit) {
        this.map = map;
        this.quit = quit;
    }

    public Map getMap() {
        return map;
    }


    public boolean getQuit() {
        return quit;
    }

}