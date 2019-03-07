package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.controler.Controler;
import fr.univ_lyon1.info.m1.poneymon_fx.model.*;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.host.Host;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.*;

/**
 * View for MenuMultiHost.
 */
public class MenuMultiHost {

    /**
     * Une String pour chaque personnage.
     */
    private String character1;

    /**
     * Une String pour la map.
     */
    private String map;

    /**
     * Booléen pour savoir si le joueur est prêt ou non.
     */
    private boolean isReady;

    /**
     * Nom du joueur.
     */
    private String name;

    /**
     * Liste contenant le personnage du joueur.
     */
    private List<String> listCharacters = new ArrayList<String>();

    /**
     * Contenu de la TableView.
     */
    private final ObservableList<Player> data = FXCollections.observableArrayList();

    /**
     * Host pour la partie réseau.
     */
    private Host host;

    /**
     * FieldModel.
     */
    FieldModel fieldModel;

    /**
     * Constructeur de la vue MenuSolo.
     */
    public MenuMultiHost(Host host, FieldModel fieldModel) {

        this.host = host;
        this.fieldModel = fieldModel;
        this.isReady = false;
        // Fenêtres
        Stage stage = new Stage();
        stage.setTitle("Créer une partie");

        Stage stage1 = new Stage();
        stage1.setTitle("Liste des joueurs");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // Bouton quitter
        Button buttonQuit = new Button("<<");
        buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuMulti mm = new MenuMulti();
                stage.close();
                stage1.close();
            }
        });
        grid.add(buttonQuit, 0, 1);

        // Choix map
        Label labelMap = new Label("Changer de map");
        grid.add(labelMap, 1, 1);

        ChoiceBox<String> choiceBoxMap = new ChoiceBox();
        choiceBoxMap.getItems().addAll("Map1", "Map2", "Map3", "Map4");
        choiceBoxMap.getSelectionModel().select(0); // Default Value
        grid.add(choiceBoxMap, 2, 1);
        //Valeur par défaut
        this.map = choiceBoxMap.getValue();
        // Listener
        choiceBoxMap.valueProperty().addListener((obs, oldValue, newValue) ->
                this.map = choiceBoxMap.getValue());


        Label labelName = new Label("Name");
        grid.add(labelName, 1, 3);
        TextField textFieldName = new TextField();
        grid.add(textFieldName, 2, 3);
        //Listener Name
        textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            this.name = textFieldName.getText();
        });
        // Spinner Générique
        SpinnerCharacter sc1 = new SpinnerCharacter();

        Spinner s1 = sc1.getSpinner();


        // Ajout sur la grille
        Label labelSpinner = new Label("Personnage");
        grid.add(labelSpinner, 1, 4);
        grid.add(s1, 2, 4);


        // Valeurs par défaut
        this.character1 = s1.getValue().toString();


        // Ajout des listeners
        s1.valueProperty().addListener((obs, oldValue, newValue) ->
                this.character1 = s1.getValue().toString());


        // Bouton jouer
        Button buttonJouer = new Button("Jouer");
        buttonJouer.setDisable(true);
        buttonJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Stage stage1 = new Stage();
                JfxView gameView = new JfxView("Poneymon 2.0 - Jeu", stage1);

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
        grid.add(buttonJouer, 2, 10);

        Button buttonPret = new Button("Prêt");
        buttonPret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                CharacterModel poney;
                if (character1.equals("lama")) {
                    poney = new LamaModel(ColorEnum.GREEN,0,false, ModeEnum.HOST,null);
                } else if (character1.equals("pony")) {
                    poney = new PoneyModel(ColorEnum.GREEN,0,false,ModeEnum.HOST,null);
                } else if (character1.equals("aquapony")) {
                    poney = new AquaPoneyModel(ColorEnum.GREEN,0,false,ModeEnum.HOST,null);
                } else if (character1.equals("ghost")) {
                    poney = new GhostPoneyModel(ColorEnum.GREEN,0,false,ModeEnum.HOST,null);
                } else {
                    poney = new PoneyModel(ColorEnum.GREEN,0,false,ModeEnum.HOST,null);
                }
                poney.setPseudo(name);
                host.getModel().setCharacter(poney,0);
                boolean ready = host.beginning();
                buttonPret.setDisable(ready);
                buttonJouer.setDisable(!ready);
            }
        });
        grid.add(buttonPret, 1, 10);

        // Provisoire
        //grid.setGridLinesVisible(true);

        // Affichage fenêtre
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();

        // --------- Création TableView ------

        TableView<String> table = new TableView();
        table.setEditable(true);

        // Create column UserName (Data type of String).
        TableColumn<String, String> userNameCol = new TableColumn<>("Username");
        userNameCol.setMinWidth(200);
        table.getColumns().addAll(userNameCol);

        // Refresher
        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        Collection<String> list = host.getReadyPoney();
                        ObservableList<String> details = FXCollections.observableArrayList(list);
                        userNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
                        table.setItems(details);
                    }
                }, 5000, 2000);

        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(table);


        Scene scene1 = new Scene(root, 200, 300);
        stage1.setScene(scene1);
        stage1.show();
    }

    /**
     * Méthode d'ajout dans la TableView.
     */
    public Player addPlayer(String username) {
        Player player = new Player(username);
        return player;
    }

    /**
     * Méthode de passage en Ready.
     */
    public void setReady() {
        this.isReady = true;
    }

    /**
     * Getter de la liste des personnages.
     * @return la liste des personnages
     */
    public String getListCharacters() {
        return  this.character1;
    }

    /**
     * Getter de la map.
     * @return String du nom de la map
     */
    public String getMap() {
        return this.map;
    }

    /**
     * Getter nom du joueur.
     * @return nom du joueur
     */
    public String getName() {
        return this.name;
    }

    /**
     * Classe temporaire pour la table.
     */
    public static class Player {
        private final String username;
        private boolean isReady;

        /**
         * Constructeur d'un player.
         * @param username nom du joueur
         */
        public Player(String username) {
            this.username = username;
            this.isReady = false;
        }

        /**
         * Méthode de mise à jour du statut Ready dans la table pour un username.
         */
        public void isReady() {
            this.isReady = true;
        }

    }
}

