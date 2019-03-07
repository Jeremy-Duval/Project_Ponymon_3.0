package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Info.FieldInfo;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.NetworkInterface;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class contain data for the field.
 *
 * @author jeremy
 */
public class FieldModel implements Serializable {
    /**
     * Liste des personnages présents dans la course.
     */
    private CharacterModel[] characters;
    /**
     * Nombre de tours à courir au total.
     */
    private int nbLapToRun = 15;
    /**
     * Gagnant de la course, null tant que la course n'est pas terminée.
     */
    private CharacterModel winner;
    /**
     * Modèle de la map à afficher.
     */
    private Map mapModel;

    private ModeEnum mode;

    private Lock fieldLock;

    private transient NetworkInterface net;

    /**
     * Constructeur de FieldModel.
     * @param charClass classe des personnages à créer (ex : PoneyModel.class)
     * @param nbAi nombre d'IA
     */
    public FieldModel(Class[] charClass, int nbAi, String mapName, 
            ModeEnum mode, NetworkInterface net) {
        Constructor cons;
        
        this.mode = mode;
        this.net = net;

        this.fieldLock = new ReentrantLock();
        
        this.characters = new CharacterModel[App.characterNumber];
        /* On initialise le terrain de course */
        ColorEnum[] colorMap = {ColorEnum.BLUE, ColorEnum.GREEN,
            ColorEnum.ORANGE, ColorEnum.PURPLE, ColorEnum.YELLOW};
        
        // on créer chaque personnage en utilisant la reflexivité.
        for (int i = 0; i < App.characterNumber; ++i) {
            try {
                if (i < nbAi) {
                    cons = charClass[i].getConstructor(ColorEnum.class, Integer.TYPE, 
                            ModeEnum.class, NetworkInterface.class);
                } else {
                    cons = charClass[i].getConstructor(ColorEnum.class, Integer.TYPE, Boolean.TYPE, 
                            ModeEnum.class, NetworkInterface.class);
                }
                try {
                    if (i < nbAi) {
                        this.characters[i] = (CharacterModel) cons
                                .newInstance(colorMap[i], i, mode, net);
                    } else {
                        this.characters[i] = (CharacterModel) cons
                                .newInstance(colorMap[i], i, true, mode, net);
                    }
                    
                    if (i > 0) {
                        this.characters[i].addNeighbor(this.characters[i - 1]);
                        this.characters[i - 1].addNeighbor(this.characters[i]);
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*this.characters[0] = new LamaModel(colorMap[0], 0, new LongerJump());
        for (int i = 1; i < App.characterNumber; ++i) {
            if (i < App.playerNumber) {
                this.characters[i] = new PoneyModel(colorMap[i], i, new NormalJump());
            } else {
                this.characters[i] = new PoneyModel(colorMap[i], i, new NormalJump(), true);
            }
            if (i > 0) {
                this.characters[i].addNeighbor(this.characters[i - 1]);
                this.characters[i - 1].addNeighbor(this.characters[i]);
            }
        }*/
        
        this.mapModel = Map.load(mapName);
    }

    public CharacterModel[] getCharacters() {
        return characters;
    }

    public int getNbLapToRun() {
        return nbLapToRun;
    }

    public Map getMapModel() {
        return mapModel;
    }

    /**
     * Cherche si un gagnant dans les characters.
     */
    private void searchWinner() {
        for (CharacterModel characterModel : this.characters) {
            if (characterModel.getCurrentLap() >= nbLapToRun) {
                this.winner = characterModel;
                return;
            }
        }
    }

    /**
     * Cherche le gagnant si il n'y en a pas encore de défini.
     *
     * @return Personnage gagnant de la course si la course est terminée ou null
     */
    public CharacterModel getWinner() {
        return winner;
    }

    /**
     * Fais évoluer les données du modèle et celles de ces enfants.
     *
     * @return True si les données du modèle ont été modifiées (ou d'un enfant)
     */
    public boolean update() {
        boolean updated = false;
        if (mode == ModeEnum.LOCAL || mode == ModeEnum.HOST) {
            for (CharacterModel model : this.characters) {
                this.updatePersoVision(model);
                // On a pas le choix sur le sens de cette instruction car si un
                // personnage a déjà bougé durant ce tour updated sera à true et
                // l'opérateur ignorera l'instruction à droite
                if (model.getCurrentLap() != this.nbLapToRun) {
                    this.mapModel.detectCollision(model);
                }
                updated = model.update() || updated;
                
            }
            if (this.winner == null) {
                this.searchWinner();
            }
        } else if (mode == ModeEnum.CUSTOMER) {
            //Le modèle est mis à jour par la partie réseau en mode client.
            updated = true;
        }
        return updated;
    }

    /**
     * Copie partielle de fieldModel.
     */
    public void lightCopy(FieldInfo fieldModel) {
        for(int i = 0; i < characters.length; i++) {
            setCharacter(fieldModel.getCharacters()[i],i);
        }
        nbLapToRun = fieldModel.getNbLapToRun();
        winner = fieldModel.getWinner();
        mapModel = fieldModel.getMapModel();
    }

    public void setCharacter(CharacterModel chara, int row) {
        characters[row] = chara;
        characters[row].setRow(row);
        characters[row].setNet(this.net);
        characters[row].setMode(this.mode);

    }

    public void setWinner(CharacterModel winner) {
        this.winner = winner;
    }

    /**
     * Fais une mise a jour partielle de ses personnages avec le fieldModel donné en paramètre.
     */
    public void updateCharacters(FieldModel model) {
        synchronized (fieldLock) {
            for (int i = 0; i < characters.length && i < model.getCharacters().length; i++) {
                if (characters[i] != null && model.getCharacters()[i] != null) {
                    this.characters[i].updateChar(model.getCharacters()[0]);
                }
            }
        }
    }
    
    /**
     * Met à jour la vision du perso.
     * @param model personnage
     */
    private void updatePersoVision(CharacterModel model) {
        int colonneNextObj;
        // on recupère la colonne (de la map) sur lequel se trouve le prochain objet.
        if (model.getCurrentLap() < this.mapModel.getNbLap()) {
            colonneNextObj = this.mapModel
                    .getNextObjectColumn(model.getRow(), 
                            model.getCurrentLap(), model.getProgress());
        } else {
            colonneNextObj = -1;
        }
        // on met à jour la vision
        if (colonneNextObj >= 0) {
            model.updateVision(this.mapModel
                    .getObject(model.getRow(), model.getCurrentLap(), colonneNextObj),
                    colonneNextObj * (1.0 / this.mapModel.getNbColumn()));
        } else {
            model.updateVision(null, 1);
        }
    }

    public void setMapModel(Map map) {
        this.mapModel = map;
    }

    public void setNet (NetworkInterface netI) {
        this.net = netI;
        for (CharacterModel poney: characters) {
            poney.setNet(netI);
        }
    }
}
