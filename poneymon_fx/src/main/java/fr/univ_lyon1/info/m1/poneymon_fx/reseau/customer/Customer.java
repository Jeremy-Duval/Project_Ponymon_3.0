package fr.univ_lyon1.info.m1.poneymon_fx.reseau.customer;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.NetworkInterface;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public class Customer implements NetworkInterface{
    private InetAddress ip;
    private int port;
    private Interface anInterface;

    /**
     * Constructeur de customer.
     * @param adress Adresse de l'hote.
     * @param p Port de l'hote.
     * @param fm FieldModel.
     */
    public Customer(InetAddress adress, int p,FieldModel fm) {
        ip = adress;
        port = p;
        anInterface = new Interface(fm,0);
    }

    /**
     * Lance le thread de connection à l'hote.
     */
    public void launch() {
        new CustomerConnect(ip, port, anInterface).start();
    }

    public void setIpAtLocalAdress() throws IOException {
        ip = InetAddress.getLocalHost();
    }

    public Interface getAnInterface() {
        return anInterface;
    }

    public void setReady(boolean b) {
        anInterface.setReady(b);
    }

    public void setQuit() {
        anInterface.setQuit(true);
    }

    public void setPower() {
        anInterface.setPwr(true);
    }

    public void setJump() {
        anInterface.setJmp(true);
    }

    public void setCharacter(CharacterModel poney) {
        anInterface.setCharacter(poney);
    }

    public void setPseudo(String pseudo) {
        this.anInterface.setPseudo(pseudo);
    }

}
