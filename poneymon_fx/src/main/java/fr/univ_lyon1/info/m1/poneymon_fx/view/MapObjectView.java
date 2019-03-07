package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.MapObject;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObjectBooster;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObjectObstacle;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObjectPuddle;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * View for the objects to place on the map.
 * 
 * @author jeremy
 */
public class MapObjectView implements View {
    /**
     * Position verticale actuelle du personnage.
     */
    private final double y;
    /**
     * Position horizontale de l'objet.
     */
    private final int x;
    /**
     * Image représentant l'objet.
     */
    private static ConcurrentHashMap<String, Image> imgObject 
            = new ConcurrentHashMap<String, Image>();
    /**
     * Contexte graphique dans lequel on va afficher l'objet.
     */
    private GraphicsContext graphicsContext;
    /**
     * Modèle de l'objet à afficher.
     */
    private MapObject mapObject;
    /**
     * Nom de la classe model de l'objet.
     */
    private String mapObjectClassName;
    
    
    /**
     * Constructeur de MapObjectView.
     *
     * @param gc      ContextGraphic dans lequel on va afficher l'objet
     * @param x position x de l'objet (en pixel)
     * @param y position y de l'objet (en pixel)
     */
    MapObjectView(GraphicsContext gc, int x, int y, MapObject objectModel) {
        if (gc != null) {
            graphicsContext = gc;
        }
        
        this.setModel(objectModel);
        
        this.x = x;
        this.y = y - imgObject.get(this.mapObjectClassName).getHeight();
    }
    
    /**
     * Charge les images du personnage.
     */
    private void loadAssets() {
        if (this.mapObject instanceof ObjectObstacle) {
            if (!imgObject.containsKey(mapObjectClassName)) {
                imgObject.put(mapObjectClassName, new Image("assets/objects/obstacle.png"));
            }
        } else if (this.mapObject instanceof ObjectPuddle) {
            if (!imgObject.containsKey(mapObjectClassName)) {
                imgObject.put(mapObjectClassName, new Image("assets/objects/puddle.png"));
            }
        } else if (this.mapObject instanceof ObjectBooster) {
            if (!imgObject.containsKey(mapObjectClassName)) {
                imgObject.put(mapObjectClassName, new Image("assets/objects/apple.png"));
            }
        }
    }
    
    /**
     * Attribut le modèle de données à prendre en charge.
     *
     * @param objectModel Modèle d'objet à gérer
     */
    private void setModel(MapObject objectModel) {
        this.mapObject = objectModel;
        mapObjectClassName = this.mapObject.getClass().toString();
        
        if (this.graphicsContext != null) {
            // On charge l'image associée à l'objet
            this.loadAssets();
        }
    }
    
    /**
     * Affichage de l'objet.
     */
    public void display() {
        graphicsContext.drawImage(imgObject.get(this.mapObjectClassName), x, y);
    }
    
}
