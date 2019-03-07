package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.controler.Controler;
import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.Map;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * view for the field.
 *
 * @author jeremy
 */
public class FieldView extends CanvasView {
    /**
     * Contexte graphique dans lequel on va afficher les éléments.
     */
    private final GraphicsContext gc;
    /**
     * Largeur de la fenêtre.
     */
    private final int width;
    /**
     * Hauteur de la fenêtre.
     */
    private final int height;
    /**
     * Tableau de vue pour afficher les personnages.
     */
    private final CharacterView[] characters;
    /**
     * Map de jeu.
     */
    private final MapView map;
    /**
     * Modèle du champ de cours.
     */
    private final FieldModel fm;
    /**
     * Controller à qui on va rapporter les évènements.
     */
    private Controler controler;

    /**
     * Constructeur de FieldView.
     *
     * @param fieldModel Modèle de données
     * @param width      Largeur du canvas
     * @param height     Hauteur du canvas
     */
    public FieldView(FieldModel fieldModel, int width, int height) {
        super(width, height);

        this.fm = fieldModel;
        characters = new CharacterView[App.characterNumber];

        this.width = width;
        this.height = height;

        /*
         * Permet de capturer le focus et donc les evenements clavier et
         * souris
         */
        this.setFocusTraversable(true);

        gc = this.getGraphicsContext2D();

        BoostKey[] boostKeys = {BoostKey.Q, BoostKey.M, BoostKey.B, BoostKey.N2,
            BoostKey.N9};
        JumpKey[] jumpKeys = {JumpKey.A, JumpKey.P, JumpKey.G, JumpKey.N1,
            JumpKey.N8};
        int lineHeight = this.height / characters.length;

        for (int i = 0; i < characters.length; i++) {
            if (i < App.playerNumber) {
                characters[i] = new CharacterView(gc, this.width, lineHeight,
                    boostKeys[i], jumpKeys[i]);
            } else {
                characters[i] = new CharacterView(gc, this.width, lineHeight);
            }
            characters[i].setModel(fieldModel.getCharacters()[i]);
        }
        
        map = new MapView(gc, width, height, this.fm.getMapModel(), this.fm.getCharacters());

        /*
         * Event Listener du clavier quand une touche est pressée on rapporte
         * l'évènement au contrôleur
         */
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                controler.keyPressed(e.getCode().getName());
            }
        });
    }

    /**
     * Affiche le personnage gagnant à l'écran.
     *
     * @param winner Personnage gagnant
     */
    private void win(CharacterModel winner) {
        gc.setFont(new Font("Comic Sans MS", 40));
        gc.strokeText("Winner : " + winner.getColor(),
                (width >> 1) - 150, height >> 1);
        // >> 1 décalage de bit -> division entière
    }

    /**
     * Affichage le terrain et les characters.
     */
    public void display() {

        // On nettoie le canvas à chaque frame
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, width, height);

        // Affichage des characters
        for (CharacterView character : characters) {
            character.display();
        }
        
        map.display();

        // On récupère le gagnant et on vérifie si il existe, si oui on
        // l'affiche
        CharacterModel winner = fm.getWinner();
        if (winner != null) {
            win(winner);
        }
    }

    /**
     * Défini le contrôleur qui est en charge de cette vue (à qui on va
     * rapporter les évènements).
     * @param controler Controler à attribuer à cette vue
     */
    public void setControler(Controler controler) {
        this.controler = controler;

        for (CharacterView view : this.characters) {
            this.controler.addSubscriber(view);
        }
    }
}
