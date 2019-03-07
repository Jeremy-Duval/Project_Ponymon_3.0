package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info;

import java.io.Serializable;

public class Connect implements Serializable {
    private String pseudo;
    private boolean quit;

    public Connect(String pseudo, boolean quit) {
        this.pseudo = pseudo;
        this.quit = quit;
    }

    public String getPseudo() {
        return pseudo;
    }

    public boolean getQuit() {
        return quit;
    }
}
