package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AquaPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.GhostPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LamaModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.observer.KeyEventSubscriber;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * view for the characters.
 *
 * @author jeremy
 */
public class CharacterView implements View, KeyEventSubscriber {
    /**
     * Largeur de la fenêtre.
     */
    private final int windowWidth;
    /**
     * Hauteur de la ligne sur laquelle évolue le personnage.
     */
    private final int lineHeight;
    /**
     * Position horizontal d'origine du personnage (relatif à la taille de
     * l'image).
     */
    private int xInit;
    /**
     * Position verticale d'origine du personnage.
     */
    private int yInit;
    /**
     * Position horizontale actuelle du personnage.
     */
    private double x;
    /**
     * Position verticale actuelle du personnage.
     */
    private double y;
    /**
     * Image représentant le personnage qui cours.
     */
    private Image runningImage;
    /**
     * Image représentant le personnage lorqu'il est boosté.
     */
    private Image poweredImage;
    /**
     * Contexte graphique dans lequel on va afficher le personnage.
     */
    private GraphicsContext graphicsContext;
    /**
     * Modèle du personnage à afficher.
     */
    private CharacterModel character;
    /**
     * Touche servant à déclencher le pouvoir du personnage.
     */
    private final BoostKey boostKey;
    /**
     * Touche servant à déclencher le saut du personnage.
     */
    private final JumpKey jumpKey;
    /**
     * Hauteur maximum pour un saut.
     */
    private final int maxJumpHeight = 50;

    /**
     * Constructeur du CharacterView.
     *
     * @param gc      ContextGraphic dans lequel on va afficher le character
     * @param width   Taille de la fenêtre (en pixel)
     * @param lHeight Hauteur d'une ligne (en pixel)
     */
    CharacterView(GraphicsContext gc, int width, int lHeight, BoostKey boostKey, JumpKey jumpKey) {
        this.windowWidth = width;
        this.lineHeight = lHeight;
        this.boostKey = boostKey;
        this.jumpKey = jumpKey;

        if (gc != null) {
            // gc == null can be provided for testing
            graphicsContext = gc;
        } else {
            xInit = 0;
        }
    }

    /**
     * Constructeur du CharacterView.
     *
     * @param gc      ContextGraphic dans lequel on va afficher le character
     * @param width   taille de la fenêtre (en pixel)
     * @param lHeight hauteur d'une ligne (en pixel)
     */
    CharacterView(GraphicsContext gc, int width, int lHeight) {
        this(gc, width, lHeight, null, null);
    }

    /**
     * Charge les images du personnage.
     */
    private void loadAssets() {
        String baseName = getBaseAssetName(this.character);

        runningImage = new Image(baseName + "-running.gif");
        poweredImage = new Image(baseName + "-powered.gif");
    }

    /**
     * Attribut le modèle de données à prendre en charge.
     *
     * @param characterModel Modèle de character à gérer
     */
    public void setModel(CharacterModel characterModel) {
        this.character = characterModel;

        this.yInit = character.getRow() * lineHeight;
        this.y = character.getRow() * lineHeight;
        if (this.graphicsContext != null) {
            // gc == null can be provided for testing

            // On charge l'image associée au character
            this.loadAssets();

            xInit = (int) -runningImage.getWidth();
            x = xInit;
        } else {
            xInit = 0;
        }
    }

    /**
     * Affichage du character.
     */
    public void display() {
        setPosition(character.getProgress(), character.getJumpProgress());

        if (this.character.isPowered()) {
            graphicsContext.drawImage(poweredImage, x, y);
        } else {
            graphicsContext.drawImage(runningImage, x, y);
        }
    }

    /**
     * Positionne (en nombre de pixel) le character sur l'axe x, en fonction du
     * pourcentage de son avancement sur la ligne.
     *
     * @param avancement progression sur l'axe des x (de 0.0 à 1.0)
     */
    private void setPosition(double avancement, double jumpProgress) {
        x = avancement * (windowWidth - xInit) + xInit;
        y = -jumpProgress * maxJumpHeight + yInit;
    }

    /**
     * Appelé lorsqu'une touche est pressée, va analyser l'évènement et
     * déclenché une action si elle est destinataire de cette évènement (sa
     * touche est la touche pressée).
     * @param s Touche pressée
     */
    public void keyPressed(String s) {
        if (this.boostKey != null && this.boostKey.equals(s)) {

            this.character.tryUsePower();
        }
        if (this.jumpKey != null && this.jumpKey.equals(s)) {
            this.character.tryUseJump();
        }
    }

    static String getBaseAssetName(CharacterModel model) {
        String basename = "";
        if (model instanceof PoneyModel) {
            basename = "pony-" + model.getColor();
        } else if (model instanceof LamaModel) {
            basename = "lama";
        } else if (model instanceof GhostPoneyModel) {
            basename = "pony-ghost";
        } else if (model instanceof AquaPoneyModel) {
            basename = "aquapony";
        }
        return "assets/" + basename;
    }
}
