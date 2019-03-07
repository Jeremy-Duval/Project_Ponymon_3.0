package fr.univ_lyon1.info.m1.poneymon_fx.reseau.host;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.FieldInfo;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.MapInfo;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Envoi;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Reception;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.Beginning;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.UpdateModel;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur.InterpreteurConnect;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur.InterpreteurJmpPwr;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur.InterpreteurReady;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;


public class HostThread extends Thread  {
    protected Socket socket;
    private Interface anInterface;

    public HostThread(Socket clientSocket, Interface anInterface) {
        this.anInterface = anInterface;
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Client connecté");
        try {
            new Reception(socket,new InterpreteurConnect(),anInterface).run();
        } catch (IOException e) {
            e.printStackTrace();
            anInterface.setQuit(true);
        }
        /*try {
            new Envoi(socket, new MapInfo(anInterface.getMap(),anInterface.getQuit())).run();
        } catch (IOException e) {
            e.printStackTrace();
            anInterface.setQuit(true);
        }*/
        try {
            new Reception(socket,new InterpreteurReady(),anInterface).run();
        } catch (IOException e) {
            e.printStackTrace();
            anInterface.setQuit(true);
        }
        synchronized (anInterface.getLock()) {
            while (!anInterface.isBeginning()) {
                try {
                    anInterface.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    anInterface.setQuit(true);
                }
            }
        }
        try {
            new Envoi(socket, new Beginning(new FieldInfo(anInterface.getModel()),anInterface.getQuit())).run();
        } catch (IOException e) {
            e.printStackTrace();
            anInterface.setQuit(true);
        }
        boolean quit = false;
        while (!quit) {
            if (anInterface.getModel().getWinner() != null) {
                anInterface.setQuit(true);
            }
            try {
                new Envoi(socket,
                        new UpdateModel(anInterface.getModel(),anInterface.getQuit())).run();
            } catch (IOException e) {
                //e.printStackTrace();
                anInterface.setQuit(true);
            }
            try {
                new Reception(socket,new InterpreteurJmpPwr(),anInterface).run();
            } catch (IOException e) {
                //e.printStackTrace();
                anInterface.setQuit(true);
            }
            quit = anInterface.getQuit();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Connection Closed");
    }
}