package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.controler.Controler;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ModeEnum;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.customer.Customer;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.host.Host;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Menu Principal qui apparait au lancement.
 */
public class MenuMulti {

    /**
     * Port de la partie.
     */
    private String port;

    /**
     * Adresse de la partie.
     */
    private String adresse;

    /**
     * Nombre d'IA.
     */
    private int nbIa;

    /**
     * Constructeur de la View MenuMulti.
     */
    public MenuMulti() {

        Stage stage = new Stage();
        // Nom de la fenetre
        stage.setTitle("Multiplayers");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


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

        // Port
        Label labelPort = new Label("Port");
        grid.add(labelPort, 0, 2);
        TextField textFieldPort = new TextField();
        grid.add(textFieldPort, 1, 2);

        //Adresse
        Label labelAdresse = new Label("Adresse");
        labelAdresse.setMinSize(60, 10);
        grid.add(labelAdresse, 0, 3);
        TextField textFieldAdresse = new TextField();
        grid.add(textFieldAdresse, 1, 3);

        // Spinner IA
        Label labelia = new Label("Nombre d'IA");
        grid.add(labelia, 0, 4);
        final Spinner<Integer> spinneria = new Spinner<Integer>();
        final int initialValueSpinnerIa = 3;
        // Value factory.
        SpinnerValueFactory<Integer> valueFactoryIa = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, initialValueSpinnerIa);

        spinneria.setValueFactory(valueFactoryIa);
        grid.add(spinneria, 1, 4);
        // Valeur par défaut
        this.nbIa = spinneria.getValue();

        Button buttonHost = new Button("Host");
        buttonHost.setDisable(true);
        buttonHost.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Class[] cl = new Class[App.characterNumber];

                for(int i = 0; i<App.characterNumber; i++) {
                    cl[i] = PoneyModel.class;
                }

                FieldModel fieldModel = new FieldModel(cl, getNbIa(),
                        "jump", ModeEnum.HOST, null);
                // -1 car on ne compte pas l'hôte
                Host host = new Host(fieldModel, Integer.parseInt(getPort()), App.characterNumber - getNbIa() - 1);
                fieldModel.setNet(host);
                MenuMultiHost mm = new MenuMultiHost(host, fieldModel);
                host.launch();
                stage.close();
            }
        });
        buttonHost.setMinSize(200, 50);
        grid.add(buttonHost, 1, 5);

        Button buttonLocal = new Button("Client");
        buttonLocal.setDisable(true);
        buttonLocal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Class[] cl = new Class[App.characterNumber];

                for(int i = 0; i<App.characterNumber; i++) {
                    cl[i] = PoneyModel.class;
                }


                FieldModel fieldModel = new FieldModel(cl, getNbIa(),
                        "jump", ModeEnum.CUSTOMER, null);

                Customer customer = null;
                try {
                    customer = new Customer(InetAddress.getByName(getAdresse()), Integer.parseInt(getPort()), fieldModel);
                    fieldModel.setNet(customer);
                    MenuMultiClient ms = new MenuMultiClient(customer, fieldModel);
                    customer.launch();
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }


                stage.close();
            }
        });
        buttonLocal.setMinSize(200, 50);
        grid.add(buttonLocal, 1, 6);

        // Lister port
        textFieldPort.textProperty().addListener((observable, oldValue, newValue) -> {
            this.port = textFieldPort.getText();
            if(!textFieldPort.getText().trim().isEmpty()) {
                buttonHost.setDisable(false);
            }
            if(textFieldPort.getText().trim().isEmpty()) {
                buttonHost.setDisable(true);
            }
            if(!textFieldAdresse.getText().trim().isEmpty() && !textFieldPort.getText().trim().isEmpty()) {
                buttonLocal.setDisable(false);
            }
            if(textFieldAdresse.getText().trim().isEmpty() || textFieldPort.getText().trim().isEmpty()) {
                buttonLocal.setDisable(true);
            }
        });

        //Listener Adresse
        textFieldAdresse.textProperty().addListener((observable, oldValue, newValue) -> {
            this.adresse = textFieldAdresse.getText();
            if(!textFieldAdresse.getText().trim().isEmpty() && !textFieldPort.getText().trim().isEmpty()) {
                buttonLocal.setDisable(false);
            }

            if(textFieldAdresse.getText().trim().isEmpty() || textFieldPort.getText().trim().isEmpty()) {
                buttonLocal.setDisable(true);
            }
        });
        Scene scene = new Scene(grid, 330, 320);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Getter du port.
     * @return le port.
     */
    public String getPort() {
        return this.port;
    }

    /**
     * Getter de l'adresse.
     * @return l'adresse de la partie.
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Getter du nombre d'IA.
     * @return nombre d'IA dans la partie
     */
    public int getNbIa() {
        return this.nbIa;
    }
}
