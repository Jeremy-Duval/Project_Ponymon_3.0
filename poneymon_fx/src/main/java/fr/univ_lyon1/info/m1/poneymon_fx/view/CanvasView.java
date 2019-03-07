package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
abstract class CanvasView extends Canvas implements View {
    Group root;

    CanvasView(int width, int height) {
        super(width, height);
    }

    void setRoot(Group root) {
        this.root = root;
    }
}
