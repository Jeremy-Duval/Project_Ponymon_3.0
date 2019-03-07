package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;

import java.io.Serializable;

public class Ready implements Serializable {
    private CharacterModel character;
    private boolean quit;

    public Ready(CharacterModel character, boolean quit) {
        this.character = character;
        this.quit = quit;
    }

    public CharacterModel getCharacter() {
        return character;
    }

    public boolean getQuit() {
        return  quit;
    }
}