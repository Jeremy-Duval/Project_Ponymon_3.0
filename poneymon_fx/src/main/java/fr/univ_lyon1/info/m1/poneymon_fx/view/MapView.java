package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.Map;
import fr.univ_lyon1.info.m1.poneymon_fx.observer.UpdateObservable;
import fr.univ_lyon1.info.m1.poneymon_fx.observer.UpdateObserver;
import javafx.scene.canvas.GraphicsContext;

/**
 * View for the map.
 *
 * @author jeremy
 */
public class MapView implements View, UpdateObserver {

    /**
     * Largeur de la fenêtre.
     */
    private final int windowWidth;
    /**
     * Hauteur de la fenêtre.
     */
    private final int windowHeight;
    /**
     * Contexte graphique dans lequel on va afficher l'objet.
     */
    private GraphicsContext graphicsContext;
    /**
     * Modèle de la map à afficher.
     */
    private Map mapModel;
    /**
     * Tableau de vue pour afficher les objets de la map.
     * [Ligne][Colonne].
     */
    private MapObjectView[][] map;
    /**
     * Tour actuel de chaque joueur.
     */
    private int[] currentLap;
    /**
     * Liste des personnages à observer.
     */
    private final CharacterModel[] characters;
    
    /**
     * Constructeur de MapObjectView.
     *
     * @param gc      ContextGraphic dans lequel on va afficher l'objet
     * @param width   largeur de la fenêtre (en pixel)
     * @param height   hauteur de la fenêtre (en pixel)
     * @param characters personnages (afin de mettre à jour la map quand ils changent de tour)
     */
    MapView(GraphicsContext gc, int width, int height, Map mapModel, CharacterModel[] characters) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.characters = characters;
        
        if (gc != null) {
            graphicsContext = gc;
        }
        
        this.setModel(mapModel);
        
        this.updateMapContent();
    }
    
    /**
     * Attribut le modèle de données à prendre en charge.
     *
     * @param mapModel Modèle de map à gérer
     */
    private void setModel(Map mapModel) {
        this.mapModel = mapModel;
        this.map = new MapObjectView[this.mapModel.getNbLine()][this.mapModel.getNbColumn()];
        this.currentLap = new int[this.mapModel.getNbLine()];
    }
    
    /**
     * Affichage de les objets de la map.
     */
    public void display() {
        for (int i = 0; i < mapModel.getNbLine(); ++i) {
            for (int j = 0; j < mapModel.getNbColumn(); ++j) {
                if (this.map[i][j] != null) {
                    this.map[i][j].display();
                }
            }
        }
        
        for (int i = 0; i < App.characterNumber; ++i) {
            final CharacterModel character = this.characters[i];
            character.addObserver(this);
            this.notifyUpdate(character);
        }
    }
    
    /**
     * Met à jour le contenu de la map.
     */
    private void updateMapContent() {
        for (int i = 0; i < this.mapModel.getNbLine(); ++i) {
            this.updateMapContent(i);
        }
    }
    
    /**
     * Met à jour le contenu de la map pour la ligne indiqué.
     */
    private void updateMapContent(int line) {
        for (int j = 0; j < this.mapModel.getNbColumn(); ++j) {
            if (this.mapModel.getObject(line, this.currentLap[line], j) != null) {
                // On divise la fenêtre en cases et on place l'objet sur une case
                // Attribue à la view de l'objet son model (pour le tour courant)
                this.map[line][j] = new MapObjectView(this.graphicsContext, 
                        this.windowWidth / this.mapModel.getNbColumn() * j, 
                        this.windowHeight / this.mapModel.getNbLine() * (line + 1), 
                        this.mapModel.getObject(line, this.currentLap[line], j));
            } else {
                this.map[line][j] = null;
            }
        }
    }

    /**
     * Met à jour la map quand un personnage change de tour.
     */
    public void notifyUpdate(UpdateObservable observable) {
        CharacterModel character = (CharacterModel) observable;
        if (this.currentLap[character.getRow()] >= this.mapModel.getNbLap()) {
            this.currentLap[character.getRow()] = 0;
        } else {
            this.currentLap[character.getRow()] = character.getCurrentLap();
        }
        this.updateMapContent(character.getRow());
    }
}
