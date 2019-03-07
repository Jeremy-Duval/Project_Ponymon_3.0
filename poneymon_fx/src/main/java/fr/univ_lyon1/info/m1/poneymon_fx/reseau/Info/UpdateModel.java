package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

import java.io.Serializable;

public class UpdateModel implements Serializable {
    private FieldModel model;
    private boolean quit;

    public UpdateModel(FieldModel model, boolean quit) {
        this.model = model;
        this.quit = quit;
    }

    public FieldModel getModel() {
        return  model;
    }

    public boolean getQuit() {
        return quit;
    }

}
