package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.Connect;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.UpdateModel;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;

import java.io.Serializable;
import java.net.Socket;

public class InterpreteurConnect implements Interpreteur, Serializable {

    @Override
    public void run(Object line, Socket socket, Interface inter) {
        if (line instanceof Connect) {
            inter.setPseudo(((Connect)line).getPseudo());
            inter.setQuit(((Connect)line).getQuit() || inter.getQuit());
        }
    }
}
