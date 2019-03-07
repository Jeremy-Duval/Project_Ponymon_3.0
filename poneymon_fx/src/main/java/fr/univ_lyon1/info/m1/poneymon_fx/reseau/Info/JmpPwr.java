package fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info;

import java.io.Serializable;

public class JmpPwr implements Serializable {
    private boolean jmp;
    private boolean pwr;
    private boolean quit;

    /**
     * Constructeur de JmpPwr.
     * @param jmp le saut a été activé.
     * @param pwr le pouvoir a été activé.
     * @param quit la partie a été quittée.
     */
    public JmpPwr(boolean jmp, boolean pwr, boolean quit) {
        this.jmp = jmp;
        this.pwr = pwr;
        this.quit = quit;
    }

    public boolean getJmp() {
        return  jmp;
    }

    public boolean getPwr() {
        return pwr;
    }

    public boolean getQuit() {
        return quit;
    }
}
