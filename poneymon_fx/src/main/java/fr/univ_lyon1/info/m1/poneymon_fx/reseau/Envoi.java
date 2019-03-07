package fr.univ_lyon1.info.m1.poneymon_fx.reseau;

import java.io.*;
import java.net.Socket;

public class Envoi implements Serializable {
    protected Socket socket;
    private Object message;

    public Envoi(Socket s,Object o) {
        socket = s;
        message = o;
    }

    /**
     * Envoie message via le socket (nécessite Reception).
     */
    public void run() throws IOException {
        InputStream inp = null;
        BufferedReader brinp = null;
        ObjectOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;

        line = brinp.readLine();
        if ((line == null) || line.equalsIgnoreCase("QUIT")) {
            socket.close();
            return;
        } else {
            out.writeObject(message);
            out.flush();
        }
    }

}


