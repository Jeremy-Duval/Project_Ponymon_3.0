package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.Beginning;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.UpdateModel;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;

import java.io.Serializable;
import java.net.Socket;

public class InterpreteurBeginning implements Interpreteur, Serializable {

    @Override
    public void run(Object line, Socket socket, Interface inter) {
        if (line instanceof Beginning) {
            inter.setModel(((Beginning)line).getModel());
            inter.setBeginning(true);
            inter.setQuit(((Beginning)line).getQuit() || inter.getQuit());
        }
    }
}
