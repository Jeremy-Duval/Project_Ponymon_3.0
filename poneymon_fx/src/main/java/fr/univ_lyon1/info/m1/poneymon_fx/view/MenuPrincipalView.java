package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.controler.Controler;
import fr.univ_lyon1.info.m1.poneymon_fx.model.AquaPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.GhostPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LamaModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ModeEnum;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Menu Principal qui apparait au lancement.
 */
public class MenuPrincipalView {
    /**
     * Longueur et largeur de la fenêtre.
     */
    private int width;
    private int height;

    /**
     * Constructeur MenuView.
     *
     * @param stage Relatif à Canvas pour la construction de la fenêtre
     * @param w     largeur de la fenêtre
     * @param h     hauteur de la fenêtre
     */
    public MenuPrincipalView(final Stage stage, int w, int h) {

        this.width = w;
        this.height = h;

        // Nom de la fenetre
        stage.setTitle("Poneymon");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Button buttonSolo = new Button("Local Game");
        buttonSolo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuLocal ms = new MenuLocal();
                stage.close();
            }
        });
        buttonSolo.setMinSize(200, 50);
        grid.add(buttonSolo, 1, 2);

        Button buttonMulti = new Button("Multiplayer Game");
        buttonMulti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuMulti mm = new MenuMulti();
                stage.close();
            }
        });
        buttonMulti.setMinSize(200, 50);
        grid.add(buttonMulti, 1, 3);

        Button buttonParameters = new Button("Paramètres");
        buttonParameters.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

            }
        });
        buttonParameters.setMinSize(200, 50);
        grid.add(buttonParameters, 1, 4);

        Button buttonExit = new Button("Exit");
        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
            }
        });
        buttonExit.setMinSize(200, 50);
        grid.add(buttonExit, 1, 5);

        Scene scene = new Scene(grid, 270, 320);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Constructeur sans paramètres.
     */
    public MenuPrincipalView() {
        Stage stage = new Stage();
        MenuPrincipalView mvp = new MenuPrincipalView(stage, 600, 600);
    }

    /**
     * Démarre le jeu.
     */
    public void startGame() {
        /* ****************A Supprimer : les joueurs doivent choisir le perso */
        Class[] cl = new Class[App.characterNumber];
        cl[0] = PoneyModel.class;
        cl[1] = LamaModel.class;
        cl[2] = AquaPoneyModel.class;
        cl[3] = GhostPoneyModel.class;
        cl[4] = PoneyModel.class;
        /* ******************************************************************* */
       
        Stage stage1 = new Stage();
        JfxView gameView = new JfxView("Poneymon 2.0 - Jeu", stage1);
        /* creation fieldModel : a faire après choix des perso ; pas dans le menu principal */
        FieldModel fieldModel = new FieldModel(cl, App.playerNumber, 
                "test", ModeEnum.LOCAL, null);
        /* ********************************************************************* */
        FieldView fieldView =
                new FieldView(fieldModel, this.width, this.height);

        Controler controler = Controler.getControler();

        fieldView.setControler(controler);
        controler.addUpdateView(gameView);
        controler.setModel(fieldModel);
        gameView.setView(fieldView);

        Stage stage2 = new Stage();
        JfxView scoreView = new JfxView("Poneymon 2.0 - Score", stage2);
        scoreView.setView(new ScoreView(fieldModel, this.width, this.height));
        controler.addView(scoreView);

        controler.startTimer();

    }
}
