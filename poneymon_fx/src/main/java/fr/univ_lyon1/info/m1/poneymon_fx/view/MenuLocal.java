package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.controler.Controler;
import fr.univ_lyon1.info.m1.poneymon_fx.model.AquaPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.GhostPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LamaModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ModeEnum;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * View for MenuLocal.
 */
public class MenuLocal {

    /**
     * Une String pour chaque personnage.
     */
    private String character1;
    private String character2;
    private String character3;
    private String character4;
    private String character5;

    /**
     * Une String pour la map.
     */
    private String map;

    /**
     * Nombre d'IA.
     */
    private int nbIa;

    /**
     * La liste des joueurs.
     * Par exemple : pony-blue, pony-green, lama, pony-orange, pony-orange.
     */
    private List<String> listCharacters = new ArrayList<String>();

    /**
     * Constructeur de la vue MenuSolo.
     */
    public MenuLocal() {
        // Fenêtre
        Stage stage = new Stage();
        stage.setTitle("Partie solo");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Button Quit

        // Bouton quitter
        Button buttonQuit = new Button("<<");
        buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuPrincipalView mpv = new MenuPrincipalView();
                stage.close();
            }
        });
        grid.add(buttonQuit, 0, 1);

        // Spinner IA
        /**
         * Ici, nombre d'IA est devenu nomre de joueurs suite à un problème de lien avec la partie Map du projet
         */
        Label labelia = new Label("Nombre de joueurs ");
        grid.add(labelia, 0, 3);
        final Spinner<Integer> spinneria = new Spinner<Integer>();
        final int initialValueSpinnerIa = 0;
        // Value factory.
        SpinnerValueFactory<Integer> valueFactoryIa = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, initialValueSpinnerIa);

        spinneria.setValueFactory(valueFactoryIa);
        grid.add(spinneria, 1, 3);
        // Valeur par défaut
        this.nbIa = spinneria.getValue();

        Label labelMap = new Label("Changer de map");
        grid.add(labelMap, 3, 3);

        ChoiceBox<String> choiceBoxMap = new ChoiceBox();
        choiceBoxMap.getItems().addAll("aquaLand", "boosted", "empty", "jump", "pikou");
        choiceBoxMap.getSelectionModel().select(0); // Default Value
        grid.add(choiceBoxMap, 4, 3);
        //Valeur par défaut
        this.map = choiceBoxMap.getValue();
        // Listener
        choiceBoxMap.valueProperty().addListener((obs, oldValue, newValue) ->
                this.map = choiceBoxMap.getValue());

        // Spinners Characters

        // Labels
        Label labelIa1 = new Label("");
        Label labelIa2 = new Label("");
        Label labelIa3 = new Label("");
        Label labelIa4 = new Label("");
        Label labelIa5 = new Label("");

        // Liste des labels IA pour listeners
        List<Label> labelsIa = new ArrayList<Label>();
        labelsIa.add(labelIa1);
        labelsIa.add(labelIa2);
        labelsIa.add(labelIa3);
        labelsIa.add(labelIa4);
        labelsIa.add(labelIa5);

        // Ajout sur la grille
        grid.add(labelIa1, 0, 5);
        grid.add(labelIa2, 1, 5);
        grid.add(labelIa3, 2, 5);
        grid.add(labelIa4, 3, 5);
        grid.add(labelIa5, 4, 5);

        // Listener spinner IA
        spinneria.valueProperty().addListener((obs, oldValue, newValue) -> {
            this.nbIa = spinneria.getValue();
            int i;
            for (i = 0; i < nbIa; i++) {
                labelsIa.get(i).setText("Joueur");
            }
            for (int j = i; j < labelsIa.size(); j++) {
                labelsIa.get(i).setText("");
            }
        });

        // Spinner Générique
        SpinnerCharacter sc1 = new SpinnerCharacter();
        SpinnerCharacter sc2 = new SpinnerCharacter();
        SpinnerCharacter sc3 = new SpinnerCharacter();
        SpinnerCharacter sc4 = new SpinnerCharacter();
        SpinnerCharacter sc5 = new SpinnerCharacter();

        // 5 copies du générique
        Spinner s1 = sc1.getSpinner();
        Spinner s2 = sc2.getSpinner();
        Spinner s3 = sc3.getSpinner();
        Spinner s4 = sc4.getSpinner();
        Spinner s5 = sc5.getSpinner();

        // Ajout sur la grille
        grid.add(s1, 0, 6);
        grid.add(s2, 1, 6);
        grid.add(s3, 2, 6);
        grid.add(s4, 3, 6);
        grid.add(s5, 4, 6);

        // Valeurs par défaut
        this.character1 = s1.getValue().toString();
        this.character2 = s1.getValue().toString();
        this.character3 = s1.getValue().toString();
        this.character4 = s1.getValue().toString();
        this.character5 = s1.getValue().toString();

        // Ajout des listeners
        s1.valueProperty().addListener((obs, oldValue, newValue) ->
                this.character1 = s1.getValue().toString());
        s2.valueProperty().addListener((obs, oldValue, newValue) ->
                this.character2 = s2.getValue().toString());
        s3.valueProperty().addListener((obs, oldValue, newValue) ->
                this.character3 = s3.getValue().toString());
        s4.valueProperty().addListener((obs, oldValue, newValue) ->
                this.character4 = s4.getValue().toString());
        s5.valueProperty().addListener((obs, oldValue, newValue) ->
                this.character5 = s5.getValue().toString());

        // Bouton jouer
        Button buttonJouer = new Button("Jouer");
        buttonJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Class[] cl = new Class[App.characterNumber];
                List<String> listCharacters = getListCharacters();

                for (int i = App.characterNumber - 1; i >= 0; i--) {
                    System.out.println(listCharacters.get(i));
                    if (listCharacters.get(i).equals("lama")) {
                        cl[i] = LamaModel.class;
                    } else if (listCharacters.get(i).equals("pony")) {
                        cl[i] = PoneyModel.class;
                    } else if (listCharacters.get(i).equals("aquapony")) {
                        cl[i] = AquaPoneyModel.class;
                    } else if (listCharacters.get(i).equals("ghostpony")) {
                        cl[i] = GhostPoneyModel.class;
                    } else {
                        cl[i] = PoneyModel.class;
                    }
                }

                System.out.println("Nb IA:" + getNbIa());
                System.out.println("GetMap:" + getMap());

                Stage stage1 = new Stage();
                JfxView gameView = new JfxView("Poneymon 2.0 - Jeu", stage1);

                FieldModel fieldModel = new FieldModel(cl, getNbIa(),
                        getMap(), ModeEnum.LOCAL, null);

                FieldView fieldView =
                        new FieldView(fieldModel, 600,600);

                Controler controler = Controler.getControler();
                fieldView.setControler(controler);
                controler.addUpdateView(gameView);
                controler.setModel(fieldModel);
                gameView.setView(fieldView);

                Stage stage2 = new Stage();
                JfxView scoreView = new JfxView("Poneymon 2.0 - Score", stage2);
                scoreView.setView(new ScoreView(fieldModel, 600, 600));
                controler.addView(scoreView);

                controler.startTimer();

                stage.close();
            }
        });
        grid.add(buttonJouer, 2, 9);

        // Provisoire
        //grid.setGridLinesVisible(true);

        // Affichage fenêtre
        Scene scene = new Scene(grid, 700, 300);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Affichage des attributs pour test.
     */
    public void getAttributes() {
        /*System.out.println("C1 : " + this.character1 + " C2 : " + this.character2 + " C3 : "
                + this.character3 + " C4 : " + this.character4 + " C5 : " + this.character5);
        System.out.println("Nb IA : " + this.nbIa);
        System.out.println("Map : " + this.map);*/
    }

    /**
     * Getter du nombre d'IA.
     * @return nombre d'IA dans la partie
     */
    public int getNbIa() {
        return this.nbIa;
    }

    /**
     * Getter de la liste des personnages.
     * @return la liste des personnages
     */
    public List<String> getListCharacters() {
        this.listCharacters.clear();
        this.listCharacters.add(character1);
        this.listCharacters.add(character2);
        this.listCharacters.add(character3);
        this.listCharacters.add(character4);
        this.listCharacters.add(character5);
        return this.listCharacters;
    }

    /**
     * Getter de la map.
     * @return String du nom de la map
     */
    public String getMap() {
        return this.map;
    }

}
