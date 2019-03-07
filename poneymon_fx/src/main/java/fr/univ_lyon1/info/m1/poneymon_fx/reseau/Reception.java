package fr.univ_lyon1.info.m1.poneymon_fx.reseau;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interpreteur.Interpreteur;

import java.io.*;
import java.net.Socket;

public class Reception implements Serializable {
    protected Socket socket;
    private Interpreteur inter;
    private Interface interf;

    /**
     * Constructeur de reception.
     * @param s socket de communication.
     * @param i permet d'interpreter le message recu
     * @param interf interface avec le modele
     */
    public Reception(Socket s, Interpreteur i,Interface interf) {
        socket = s;
        inter = i;
        this.interf = interf;
    }

    /**
     *  Recoie le message via le socket et l'interprete(nécessite Envoi).
     */
    public void run() throws IOException {
        InputStream inp;
        ObjectInputStream brinp;
        PrintWriter out;

        inp = socket.getInputStream();
        brinp = new ObjectInputStream(inp);
        out = new PrintWriter(socket.getOutputStream());


        Object line = null;
        try {
            out.println("RDY");
            out.flush();
            line = brinp.readObject();
            inter.run(line,socket,interf);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            inter.run(null,socket,interf);
        }


    }
}