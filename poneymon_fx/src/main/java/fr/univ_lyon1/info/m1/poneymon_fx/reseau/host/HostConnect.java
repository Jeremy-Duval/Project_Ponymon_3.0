package fr.univ_lyon1.info.m1.poneymon_fx.reseau.host;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;


import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;


public class HostConnect extends Thread  {

    private int port;
    private FieldModel model;
    private boolean running = true;
    private ArrayList<Interface> inter;
    private ArrayList<HostThread> enfants;
    private int nombreJoueurs;

    /**
     * Constructeur de HostConnect.
     */
    public HostConnect(int p, FieldModel fm,int nombreJoueurs) {
        model = fm;
        port = p;
        this.nombreJoueurs = nombreJoueurs;
    }


    /**
     * Crée des sockets lorsque les clients se connectent.
     */
    @Override
    public void run() {
        inter = new ArrayList<>();
        enfants = new ArrayList<>();
        Socket s = null;
        ServerSocket ss = null;
        //System.out.println("Ouverture de la connexion Hote");
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 1;

        while (running) {
            try {
                assert ss != null;
                s = ss.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // Création d' un thread pour un client
            inter.add(new Interface(model, i));
            if (i > nombreJoueurs) {  //condition sur le nombre de clients connectables
                inter.get(i - 1).setQuit(true);
            }
            HostThread temp = new HostThread(s, inter.get(i - 1));
            enfants.add(temp);
            temp.start();
            i++;
        }
        for (HostThread miniHost : enfants) {
            try {
                miniHost.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            assert ss != null;
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Verifie que tout les clients sont prets.
     * et leur envoie que la partie va commencer le cas echeant.
     */
    public boolean askBeginning() {
        boolean ready = true;
        int i;
        for (i = 0; i < inter.size() && i < nombreJoueurs; i++) {
            ready = ready && inter.get(i).isReady();
        }

        if (i < nombreJoueurs) {
            ready = false;
        }

        if (ready) {
            for (Interface anInter : inter) {
                anInter.setBeginning(true);
            }
        }
        return ready;
    }

    public ArrayList<Interface> getInter() {
        return inter;
    }

    /**
     * Envoie a thread l arret de la connection.
     * Arrete la creation de nouveaux sockets.
     */
    public void setQuit() {
        for (Interface anInter : inter) {
            anInter.setQuit(true);
        }
        running = false;
    }

    public Collection<String> getReadyPoney() {
        Collection<String> temp = new ArrayList<String>();
        for (Interface anInter: inter) {
            if(anInter.isReady()) {
                temp.add(anInter.getPseudo());
            }
        }
        return temp;
    }
}
