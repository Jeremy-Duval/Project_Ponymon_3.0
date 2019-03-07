package fr.univ_lyon1.info.m1.poneymon_fx.reseau;

import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.Map;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.FieldInfo;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Interface implements Serializable {
    private volatile String pseudo;
    private volatile CharacterModel character;
    private volatile Map map;
    private volatile FieldModel model;
    private volatile boolean quit;
    private volatile boolean jmp;
    private volatile boolean pwr;
    private volatile int position;
    private volatile boolean ready = false;
    private volatile boolean beginning = false;
    private final Lock lock;


    /**
     * Constructeur de interface.
     */
    public Interface(FieldModel model, int position) {
        this.model = model;
        this.position = position;
        this.pseudo = "Unknown";
        lock = new ReentrantLock();
        this.map = model.getMapModel();
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public CharacterModel getCharacter() {
        return character;
    }

    public void setCharacter(CharacterModel character) {
        this.character = character;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }


    public FieldModel getModel() {
        return model;
    }

    public void setModel(FieldInfo model) {
        this.model.lightCopy(model);
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public boolean getQuit() {
        return quit;
    }

    /**
     * Renvoie Jmp si il est vrai et le set à false.
     */
    public boolean getAndResetJmp() {
        if (jmp) {
            jmp = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Renvoie Pwr si il est vrai et le set à false.
     */
    public boolean getAndResetPwr() {
        if (pwr) {
            pwr = false;
            return true;
        } else {
            return false;
        }
    }

    public void setJmp(boolean jmp) {
        this.jmp = jmp;
    }

    public void setPwr(boolean pwr) {
        this.pwr = pwr;
    }

    /**
     * Utilise le saut du poney correspondant.
     */
    public void useJmp() {
        if (position < 5) {
            if (model.getCharacters()[position] != null) {
                model.getCharacters()[position].useJump();
            }
        }
    }

    /**
     * Utilise le pouvoir du poney correspondant.
     */
    public void usePwr() {
        if (position < 5) {
            if (model.getCharacters()[position] != null) {
                model.getCharacters()[position].usePower();
            }
        }
    }

    /**
     * set Ready et reveille le thread correspondant a cette interface.
     */
    public void setReady(boolean b) {
        synchronized (this.lock) {
            this.ready = b;
            lock.notifyAll();
        }
    }

    public boolean isReady() {
        return ready;
    }

    public boolean isBeginning() {
        return beginning;
    }

    /**
     * set Beginning et reveille le thread correspondant a cette interface.
     */
    public void setBeginning(boolean beginning) {
        synchronized (this.lock) {
            this.beginning = beginning;
            lock.notifyAll();
        }
    }


    public Lock getLock() {
        return lock;
    }

    public int getPosition() {
        return position;
    }

    public void updateModel(FieldModel model) {
        this.model.updateCharacters(model);
        this.model.setWinner(model.getWinner());
    }

    public void updateMap(Map map) {
        model.setMapModel(map);
    }

}
