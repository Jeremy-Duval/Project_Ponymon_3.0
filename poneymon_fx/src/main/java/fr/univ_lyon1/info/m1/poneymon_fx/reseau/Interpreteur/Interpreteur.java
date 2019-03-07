package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;

import java.io.Serializable;
import java.net.Socket;

public interface Interpreteur {
    void run(Object line, Socket socket, Interface inter);
}
