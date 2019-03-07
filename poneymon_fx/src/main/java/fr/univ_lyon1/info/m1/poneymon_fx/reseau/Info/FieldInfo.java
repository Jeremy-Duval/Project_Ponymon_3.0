package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.Map;

import java.io.Serializable;

public class FieldInfo implements Serializable {

    private final CharacterModel[] characters;
    private final int nbLapToRun;
    private final CharacterModel winner;
    private final Map mapModel;

    public FieldInfo(FieldModel fieldModel) {
        characters = fieldModel.getCharacters();
        for ( CharacterModel chara:characters) {
            chara.setNet(null);
        }
        nbLapToRun = fieldModel.getNbLapToRun();
        winner = fieldModel.getWinner();
        mapModel = fieldModel.getMapModel();
    }

    public CharacterModel[] getCharacters() {
        return characters;
    }

    public int getNbLapToRun() {
        return nbLapToRun;
    }

    public CharacterModel getWinner() {
        return  winner;
    }

    public Map getMapModel() {
        return mapModel;
    }
}
