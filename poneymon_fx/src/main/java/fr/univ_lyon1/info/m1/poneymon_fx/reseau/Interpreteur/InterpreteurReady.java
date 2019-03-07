package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.Ready;

import java.io.Serializable;
import java.net.Socket;

public class InterpreteurReady implements Interpreteur, Serializable {

    @Override
    public void run(Object line, Socket socket, Interface inter) {
        if (line instanceof Ready) {
            inter.getModel().setCharacter(((Ready)line).getCharacter(),inter.getPosition());
            inter.setReady(true);
            inter.setQuit(((Ready)line).getQuit() || inter.getQuit());
            inter.setPseudo(((Ready)line).getCharacter().getPseudo());
        }
    }
}
