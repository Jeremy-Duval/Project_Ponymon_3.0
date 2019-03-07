package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.view.MenuPrincipalView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale de l'application.
 *
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Nombre de personnages dans le jeu.
     */
    public static final int characterNumber = 5;
    /**
     * Nombre de personnages contrôlés par des joueurs dans le jeu.
     */
    public static final int playerNumber = App.characterNumber >> 1;

    /**
     * En javafx start() lance l'application.
     *
     * On cree le SceneGraph de l'application ici
     *
     * @see <a
     * href="http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm">jfxpub-scenegraph.htm</a>
     */
    @Override
    public void start(Stage stage) {

        int height = 600;
        int width = 600;

        MenuPrincipalView v = new MenuPrincipalView(stage, width, height); // 600x600 pixels
    }
}
