package fr.univ_lyon1.info.m1.poneymon_fx.reseau.host;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.NetworkInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Host implements NetworkInterface {
    private final int nombreJoueurs;
    private HostConnect hc;
    private FieldModel model;
    private int port;

    /**
     * Cosntructeur de Host.
     * @param fm modele.
     * @param p  port de connection.
     * @param nombreJoueurs nombre de clients attendus.
     */
    public Host(FieldModel fm, int p, int nombreJoueurs) {
        port = p;
        model = fm;
        this.nombreJoueurs = nombreJoueurs;
    }

    public void launch() {
        System.out.println("host");
        hc = new HostConnect(port, model, nombreJoueurs);
        hc.start();
    }

    public boolean beginning() {
        return hc.askBeginning();
    }

    public HostConnect getHc() {
        return hc;
    }

    /**
     * Envoie l'arret du mode hote.
     */
    public void setQuit() {
        hc.setQuit();
    }

    public Collection<String> getReadyPoney(){
        return hc.getReadyPoney();
    }


    public FieldModel getModel() {
        return model;
    }
}
