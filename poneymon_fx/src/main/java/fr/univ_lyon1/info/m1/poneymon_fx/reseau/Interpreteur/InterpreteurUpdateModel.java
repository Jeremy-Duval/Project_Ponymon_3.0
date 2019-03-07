package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.UpdateModel;

import java.io.Serializable;
import java.net.Socket;

public class InterpreteurUpdateModel implements Interpreteur, Serializable {

    @Override
    public void run(Object line, Socket socket, Interface inter) {
        if (line instanceof UpdateModel) {
            inter.updateModel(((UpdateModel)line).getModel());
            inter.setQuit(((UpdateModel)line).getQuit() || inter.getQuit());
        }
    }
}
