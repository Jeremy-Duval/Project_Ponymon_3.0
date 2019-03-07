package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.controler.Controler;
import fr.univ_lyon1.info.m1.poneymon_fx.model.CharacterModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.JumpState;
import fr.univ_lyon1.info.m1.poneymon_fx.observer.UpdateObservable;
import fr.univ_lyon1.info.m1.poneymon_fx.observer.UpdateObserver;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public class ScoreView extends CanvasView implements UpdateObserver {
    /**
     * Largeur de la fenêtre.
     */
    private final int width;
    /**
     * Hauteur de la fenêtre.
     */
    private final int height;
    /**
     * Modèle des données à afficher.
     */
    private final FieldModel fieldModel;
    /**
     * Contexte graphique dans lequel on va réaliser les affichages.
     */
    private final GraphicsContext graphicsContext;
    /**
     * Hauteur de chaque ligne.
     */
    private final int lineHeight;

    /**
     * Constructeur de ScoreView.
     *
     * @param fieldModel Modèle de données à gérer
     * @param width      Largeur de la fenêtre d'affichage
     * @param height     Hauteur de la fenêtre d'affichage
     */
    public ScoreView(FieldModel fieldModel, int width, int height) {
        super(width, height);

        this.width = width;
        this.height = height;
        this.fieldModel = fieldModel;
        this.graphicsContext = this.getGraphicsContext2D();

        this.lineHeight = height / App.characterNumber;
    }

    /**
     * Initialise l'affichage de la fenêtre.
     */
    public void display() {
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, width, height);
        VBox box = new VBox();
        VBox box2 = new VBox();
        box.setSpacing(lineHeight - 30);
        box2.setSpacing(lineHeight - 30);
        this.root.getChildren().add(box);
        this.root.getChildren().add(box2);
        Button pause = new Button("Play/Pause");
        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Controler.getControler().togglePause();
            }
        });
        pause.setLayoutX(400);
        this.root.getChildren().add(pause);
        for (int i = 0; i < App.characterNumber; ++i) {
            final CharacterModel character = this.fieldModel.getCharacters()[i];
            graphicsContext.drawImage(new Image(CharacterView.getBaseAssetName(
                fieldModel.getCharacters()[i]) + ".gif"), 0,
                this.lineHeight * i);
            character.addObserver(this);
            this.notifyUpdate(character);
            Button boost = new Button("Utiliser pouvoir");
            boost.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    character.tryUsePower();
                }
            });
            Button jump = new Button("Sauter");
            jump.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    character.tryUseJump();
                }
            });
            if (character.isAi()) {
                boost.setDisable(true);
                jump.setDisable(true);
            }
            box.getChildren().add(boost);
            box.setLayoutX(10);
            box2.getChildren().add(jump);
            box.setLayoutX(100);
        }
    }

    /**
     * Met à jour les données de la fenêtre qui ont été modifiées.
     */
    public void notifyUpdate(UpdateObservable observable) {
        CharacterModel character = (CharacterModel) observable;
        int row = character.getRow();
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(200, lineHeight * row, width - 200,
            lineHeight);
        graphicsContext.setFont(new Font("Comic Sans MS", 20));
        graphicsContext.strokeText("Tour " + character.getCurrentLap(), 200,
            this.lineHeight * row + (this.lineHeight - 20 >> 1));
        graphicsContext.strokeText("Boosts restants " + character.getRemainingPower(),
            350, this.lineHeight * row + (this.lineHeight - 20 >> 1));
    }
}
