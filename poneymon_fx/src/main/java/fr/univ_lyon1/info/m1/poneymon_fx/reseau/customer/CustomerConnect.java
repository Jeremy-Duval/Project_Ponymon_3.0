package fr.univ_lyon1.info.m1.poneymon_fx.reseau.customer;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Envoi;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Reception;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.Connect;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.JmpPwr;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.Ready;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur.InterpreteurBeginning;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur.InterpreteurMap;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur.InterpreteurUpdateModel;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class CustomerConnect extends Thread  {
    private Interface inter;
    private InetAddress address;
    private Socket s1 = null;
    protected int port;

    /**
     * Constructeur de coustomerconnect.
     * @param ip addresse de l'hote.
     * @param port port de l'hote.
     * @param inter interface avec le modele.
     */
    CustomerConnect(InetAddress ip, int port, Interface inter) {
        this.address = ip;
        this.port = port;
        this.inter = inter;
    }



    @Override
    public void run() {
        //System.out.println("Ouverture de la connexion Client");
        try {
            s1 = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        try {
            new Envoi(s1, new Connect(inter.getPseudo(),inter.getQuit())).run();
        } catch (IOException e) {
            e.printStackTrace();
            inter.setQuit(true);
        }
        /*try {
            new Reception(s1,new InterpreteurMap(),inter).run();
        } catch (IOException e) {
            e.printStackTrace();
            inter.setQuit(true);
        }*/
        synchronized (inter.getLock()) {
            while (!inter.isReady()) {
                try {
                    inter.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    inter.setQuit(true);
                }
            }
        }
        try {
            new Envoi(s1, new Ready(inter.getCharacter(),inter.getQuit())).run();
        } catch (IOException e) {
            e.printStackTrace();
            inter.setQuit(true);
        }
        try {
            new Reception(s1,new InterpreteurBeginning(),inter).run();
        } catch (IOException e) {
            e.printStackTrace();
            inter.setQuit(true);
        }
        boolean quit = false;
        while (!quit) {
            try {
                new Reception(s1,new InterpreteurUpdateModel(),inter).run();
            } catch (IOException e) {
                //e.printStackTrace();
                inter.setQuit(true);
            }
            try {
                new Envoi(s1, new JmpPwr(inter.getAndResetJmp(),
                        inter.getAndResetPwr(),inter.getQuit())).run();
            } catch (IOException e) {
                //e.printStackTrace();
                inter.setQuit(true);
            }
            quit = inter.getQuit();
        }
        try {
            s1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Connection Closed");
    }
}
