package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur;


import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.JmpPwr;

import java.io.Serializable;
import java.net.Socket;

public class InterpreteurJmpPwr implements Interpreteur, Serializable {

    @Override
    public void run(Object line, Socket socket, Interface inter) {
        if (line instanceof JmpPwr) {
            inter.setQuit(((JmpPwr)line).getQuit() || inter.getQuit());
            if (((JmpPwr)line).getJmp()) {
                inter.useJmp();
            }
            if (((JmpPwr)line).getPwr()) {
                inter.usePwr();
            }
        }
    }
}
