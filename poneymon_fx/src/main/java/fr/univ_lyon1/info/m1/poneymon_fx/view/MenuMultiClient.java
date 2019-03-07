package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controler.Controler;
import fr.univ_lyon1.info.m1.poneymon_fx.model.*;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.Interface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.customer.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;

/**
 * View for MenuMultiHost.
 */
public class MenuMultiClient {

    /**
     * Une String pour chaque personnage.
     */
    private String character1;

    /**
     * Boolean pour savoir si le joueur est prêt.
     */
    private boolean isReady;

    /**
     * Nom du joueur.
     */
    private String name;

    /**
     * La liste contenant le personnage du joueur.
     */
    private List<String> listCharacters = new ArrayList<String>();

    /**
     * Customer représente le client.
     */
    Customer customer;

    /**
     * FieldModel.
     */
    FieldModel fieldModel;

    /**
     * Constructeur de la vue MenuSolo.
     */
    public MenuMultiClient(Customer customer, FieldModel fieldModel) {

        this.customer = customer;
        this.fieldModel = fieldModel;
        this.isReady = false;
        // Fenêtre
        Stage stage = new Stage();
        stage.setTitle("Rejoindre une partie");

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
            }
        });
        grid.add(buttonQuit, 0, 1);

        /*
        // Port
        Label labelPort = new Label("Port");
        grid.add(labelPort, 1, 1);
        TextField textFieldPort = new TextField();
        grid.add(textFieldPort, 2, 1);
        // Lister port
        textFieldPort.textProperty().addListener((observable, oldValue, newValue) -> {
            this.port = textFieldPort.getText();
        });

        //Adresse
        Label labelAdresse = new Label("Adresse");
        grid.add(labelAdresse, 1, 2);
        TextField textFieldAdresse = new TextField();
        grid.add(textFieldAdresse, 2, 2);
        //Listener Adresse
        textFieldAdresse.textProperty().addListener((observable, oldValue, newValue) -> {
            this.adresse = textFieldAdresse.getText();
        });
*/
        Label labelName = new Label("Name");
        grid.add(labelName, 1, 3);
        TextField textFieldName = new TextField();
        grid.add(textFieldName, 2, 3);
        //Listener Adresse
        textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            this.name = textFieldName.getText();
            customer.setPseudo(this.name);
        });

        // Spinners Characters

        // Spinner Générique
        SpinnerCharacter sc1 = new SpinnerCharacter();


        // 5 copies du générique
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


        Button buttonPret = new Button("Prêt");
        buttonPret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setReady();
                buttonPret.setDisable(true);
                CharacterModel poney;
                if (character1.equals("lama")) {
                    poney = new LamaModel(ColorEnum.GREEN,0,false,ModeEnum.HOST,null);
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
                customer.setCharacter(poney);
                customer.setReady(true);

                /*
                // Refresher
                new Timer().schedule(
                        new TimerTask() {

                            @Override
                            public void run() {
                                if(customer.getAnInterface().isBeginning()) {
                                    Stage stage1 = new Stage();
                                    JfxView gameView = new JfxView("Poneymon 2.0 - Jeu", stage1);
                                    FieldView fieldView = new FieldView(fieldModel, 600,600);
                                    Controler controler = Controler.getControler();
                                    fieldView.setControler(controler);
                                    controler.addUpdateView(gameView);
                                    controler.setModel(fieldModel);
                                    gameView.setView(fieldView);
                                    controler.startTimer();

                                    stage.close();
                                }
                            }
                        }, 0, 500);*/
                /*while (!customer.getAnInterface().isBeginning())
                    synchronized (customer.getAnInterface().getLock()) {
                        try {
                            customer.getAnInterface().getLock().wait();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        Stage stage1 = new Stage();
                        JfxView gameView = new JfxView("Poneymon 2.0 - Jeu", stage1);
                        FieldView fieldView = new FieldView(fieldModel, 600,600);
                        Controler controler = Controler.getControler();
                        fieldView.setControler(controler);
                        controler.addUpdateView(gameView);
                        controler.setModel(fieldModel);
                        gameView.setView(fieldView);
                        controler.startTimer();

                        stage.close();
                    }*/
            }
        });
        grid.add(buttonPret, 2, 5);

        // Provisoire
        //grid.setGridLinesVisible(true);

        // Affichage fenêtre
        Scene scene = new Scene(grid, 370, 250);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Affichage des attributs pour test.
     */
    public void getAttributes() {
        /*System.out.println("isReady : " + this.isReady);
        System.out.println("C1 : " + this.character1);
        System.out.println("Port : " + this.port);
        System.out.println("Adresse : " + this.adresse);
        System.out.println("Name : " + this.name);*/

    }

    /**
     * Setter du booléen isReady.
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
     * Getter nom du joueur.
     * @return nom du joueur
     */
    public String getName() {
        return this.name;
    }
}
