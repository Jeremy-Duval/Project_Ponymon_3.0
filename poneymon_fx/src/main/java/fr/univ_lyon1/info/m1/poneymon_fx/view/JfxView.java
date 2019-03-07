package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * view for jfx.
 *
 * @author jeremy
 */
public class JfxView implements View {
    /**
     * Groupe de vue à la racine de la fenêtre.
     */
    private final Group root;
    /**
     * Fenêtre à gérer.
     */
    private final Stage stage;
    /**
     * Vue à afficher dans la fenêtre.
     */
    private CanvasView view;

    /**
     * Constructeur de JfxView.
     *
     * @param title Titre de la fenêtre à créer
     * @param stage Fenêtre à gérer
     */
    public JfxView(String title, Stage stage) {
        this.stage = stage;

        // Nom de la fenetre
        this.stage.setTitle(title);


        this.root = new Group();
        Scene scene = new Scene(root);

        // On ajoute la scene a la fenetre
        this.stage.setScene(scene);

    }

    /**
     * Défini la vue à afficher dans la fenêtre.
     *
     * @param view Vue à attribuer dans la fenêtre
     */
    public void setView(CanvasView view) {
        this.view = view;
        this.view.setRoot(this.root);
        this.root.getChildren().add(this.view);
    }

    /**
     * Affiche la fenêtre.
     */
    public void show() {
        this.stage.show();
    }

    /**
     * Met à jour la fenêtre et sa vue.
     */
    public void display() {
        this.view.display();
    }
}
