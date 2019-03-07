package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Modele de terrain pour les courses de poneys.
 * @author jeremy
 */
public class Map implements Serializable {
    /**
     * Matrice 3D [ligne][tour][colonne] symbolisant la map.
     * Exemple : [1][5][4] représente la 4e case objet au 5e tour pour le joueur 1.
     */
    private MapObject[][][] map;
    /**
     * Nombre de piste de course.
     */
    private final int nbLine = 5;
    /**
     * Nombre de "cases" (nombre d'objet max) par tour de course.
     */
    private final int nbColumn = 5;
    /**
     * Nombre de tour d'une course.
     */
    private final int nbLap = 15;
    
    public Map() {
        map = new MapObject[nbLine][nbLap][nbColumn];
    }
    
    /**
     * Place un objet à l'emplacement donné sur chaque parcours.
     * Exemple : on place un obstacle à la colonne 4 du 8e tour.
     * 
     * @param o objet à placer
     * @param lap tour sur lequel placer l'objet
     * @param column colonne sur laquelle placer l'objet
     */
    public void setObjectToAllLine(MapObject o, int lap, int column) {
        for (int line = 0; line < this.nbLine; ++line) {
            this.map[line][lap][column] = o;
        }
    }
    
    /**
     * Place un objet à l'emplacement donné sur un parcours donné.
     * Exemple : on place un obstacle à la colonne 4 du 8e tour 
     * pour le personnage de la ligne 2.
     * 
     * @param o objet à placer
     * @param line ligne sur laquelle plcer l'objet
     * @param lap tour sur lequel placer l'objet
     * @param column colonne sur laquelle placer l'objet
     */
    public void setObject(MapObject o, int line, int lap, int column) {
        this.map[line][lap][column] = o;
    }
    
    /**
     * Renvoie l'objet à la position indiquée.
     * Exemple : Je veux l'objet colonne 2 du tour 4 pour la ligne du 3e perso.
     * 
     * @param line ligne sur laquelle se trouve l'objet
     * @param lap tour sur lequel se trouve l'objet
     * @param column colonne sur laquelle se trouve l'objet
     * @return null s'il n'y a pas d'objet, sinon retourne l'objet
     */
    public MapObject getObject(int line, int lap, int column) {
        return this.map[line][lap][column];
    }

    public int getNbLine() {
        return nbLine;
    }

    public int getNbColumn() {
        return nbColumn;
    }

    public int getNbLap() {
        return nbLap;
    }
    
    /**
     * Sauvegarde la map dans un fichier.
     * 
     * @param mapName name of the map file
     * @return true si la map a été sauvegarée, false sinon
     */
    public boolean save(String mapName) {
        ObjectOutputStream oos;
        boolean saveSuccess = true;
        
        if (isDirectoryMapExist()) {
            try {
                oos = new ObjectOutputStream(
                        new BufferedOutputStream(
                          new FileOutputStream(
                            new File("map/" + mapName))));
                // Sauvegarde de la map dans le fichier
                oos.writeObject(this);

                // Fermeture du flux
                oos.close();

            } catch (IOException ex) { 
                Logger.getLogger(Map.class.getName())
                        .log(Level.SEVERE, "Impossible de sauvegarder la map : {0}", ex);
                saveSuccess = false;
            }
        } else {
            Logger.getLogger(Map.class.getName())
                    .log(Level.SEVERE, "Impossible de trouver le dossier map et de le creer.");
            saveSuccess = false;
        }
        
        return saveSuccess;
    }
    
    /**
     * Charge la map depuis un fichier.
     * 
     * @param mapName nom du fichier map
     * @return la map chargée depuis le fichier, null sinon
     */
    public static Map load(String mapName) {
        ObjectInputStream ois;
        Map loadedMap = null;
        
        if (isDirectoryMapExist()) {
            try {
                ois = new ObjectInputStream(
                        new BufferedInputStream(
                          new FileInputStream(
                            new File("map/" + mapName))));
                // Chargement de la map depuis le fichier
                loadedMap = (Map) ois.readObject();

                // Fermeture du flux
                ois.close();

            } catch (IOException ex) { 
                Logger.getLogger(Map.class.getName())
                        .log(Level.SEVERE, "Impossible de charger la map : {0}", ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Map.class.getName())
                        .log(Level.SEVERE, "Impossible de charger la map : {0}", ex);
            }
        } else {
            Logger.getLogger(Map.class.getName())
                    .log(Level.SEVERE, "Impossible de trouver le dossier map et de le creer.");
        }
        
        return loadedMap;
    }
    
    /**
     * Test si le répertoire map exist. Si ce n'est pas le cas, on le créait.
     * 
     * @return true si le répertoire map exist ou a pu être créé, false sinon
     */
    private static boolean isDirectoryMapExist() {
        Path path;
        boolean directoryExist = true;
        
        path = Paths.get("map");
        // Si le répertoire map n'existe pas encore, on le créer
        if (!(Files.exists(path) && (Files.isDirectory(path)))) {
            try {
                Files.createDirectory(path);
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName())
                        .log(Level.SEVERE, "Impossible de creer le dossier map : {0}", ex);
                directoryExist = false;
            }
        }
        
        return directoryExist;
    }
    
    /**
     * Retourne le prochain objet sur la map à partir du point de progression.
     * @param line ligne  sur laquelle chercher
     * @param lap tour pour lequelle chercher
     * @param progress progression (entre 0 et 1) à partir de laquelle chercher.
     * @return prochain objet sur la map, null s'il n'y en a pas
     */
    public MapObject getNextObject(int line, int lap, double progress) {
        MapObject mo = null;
        double progressStep;
        int nbColumPerso;
        
        progressStep = 1.0 / this.nbColumn;
        
        for (nbColumPerso = 0; 
                progressStep * (nbColumPerso + 1) < progress; ++nbColumPerso) {}
        
        while ((nbColumPerso < this.nbColumn) && (mo == null)) {
            mo = this.getObject(line, lap, nbColumPerso);
            ++nbColumPerso;
        }
        
        return mo;
    }
    
    /**
     * Retourne la colonne du prochain objet sur la map à partir du point de progression.
     * @param line ligne  sur laquelle chercher
     * @param lap tour pour lequelle chercher
     * @param progress progression (entre 0 et 1) à partir de laquelle chercher.
     * @return colonne du prochain objet sur la map, -1 s'il n'y en a pas
     */
    public int getNextObjectColumn(int line, int lap, double progress) {
        MapObject mo = null;
        double progressStep;
        int nbColumPerso;
        
        progressStep = 1.0 / this.nbColumn;
        
        for (nbColumPerso = 0; 
                progressStep * (nbColumPerso + 1) < progress; ++nbColumPerso) {}
        
        while ((nbColumPerso < this.nbColumn) && (mo == null)) {
            mo = this.getObject(line, lap, nbColumPerso);
            ++nbColumPerso;
        }
        --nbColumPerso;
        
        if (mo == null) {
            nbColumPerso = -1;
        }
        
        return nbColumPerso;
    }
    
    /**
     * Si le personnage est en collision avec un objet applique l'effet correspondant.
     * 
     * @param c personnage dont l'on souhaite savoir s'il est en collision
     */
    public void detectCollision(CharacterModel c) {
        if (c.getJumpProgress() < 0.2 && c.getProgress() > 0.05 && c.getProgress() < 0.8) {
            int col;
            double pos = c.getProgress();
            
            if (pos < 0.2) {
                col = 0;
            } else {
                if (pos < 0.35) {
                    col = 1;
                } else {
                    if (pos < 0.5) {
                        col = 2;
                    } else {
                        if (pos < 0.65) {
                            col = 3;
                        } else {
                            col = 4;
                        }
                    }
                }
            }
            
            MapObject mo = this.getObject(c.getRow(), c.getCurrentLap(), col);
            if (mo != null) {
                mo.objEffect.applyEffect(c);
            }
        }
    }
}
