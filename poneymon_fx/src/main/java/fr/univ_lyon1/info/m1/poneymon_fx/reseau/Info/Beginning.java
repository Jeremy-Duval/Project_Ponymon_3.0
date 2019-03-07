package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

import java.io.Serializable;

public class Beginning implements Serializable {
    private FieldInfo model;
    private boolean quit;

    public Beginning(FieldInfo model, boolean quit) {
        this.model = model;
        this.quit = quit;
    }

    public FieldInfo getModel() {
        return  model;
    }

    public boolean getQuit() {
        return quit;
    }
}
