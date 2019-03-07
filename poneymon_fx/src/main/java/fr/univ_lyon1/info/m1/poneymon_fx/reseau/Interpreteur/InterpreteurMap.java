package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur;


import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.MapInfo;

import java.io.Serializable;
import java.net.Socket;

public class InterpreteurMap implements Interpreteur, Serializable {

    @Override
    public void run(Object line, Socket socket, Interface inter) {
        if (line instanceof MapInfo) {
            inter.updateMap(((MapInfo)line).getMap());
            inter.setQuit(((MapInfo)line).getQuit() || inter.getQuit());
        }
    }
}
