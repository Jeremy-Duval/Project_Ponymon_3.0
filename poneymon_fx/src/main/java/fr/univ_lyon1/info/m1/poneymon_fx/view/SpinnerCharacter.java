package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Spinner character choice for solo and multi players games.
 */
public class SpinnerCharacter {

    /**
     * Le spinner retourné par la vue.
     */
    final Spinner<String> spinnerCharacter = new Spinner<String>();
    /**
     * La liste des personnages possibles.
     */
    private List<String> poneys = new ArrayList<String>();

    /**
     * Constructeur d'un Spinner.
     */
    public SpinnerCharacter() {
        String lama = "lama";
        String pony = "pony";
        String aquapony = "aquapony";
        String ghostpony = "ghostpony";

        poneys.add(lama);
        poneys.add(pony);
        poneys.add(aquapony);
        poneys.add(ghostpony);


        // Value factory.
        SpinnerValueFactory<String> valueFactory = //
            new SpinnerValueFactory<String>() {

                @Override
                public void decrement(int steps) {
                    String current = this.getValue();
                    int idx = poneys.indexOf(current);
                    int newIdx = (poneys.size() + idx - steps) % poneys.size();
                    String newImg = poneys.get(newIdx);
                    this.setValue(newImg);
                }

                @Override
                public void increment(int steps) {
                    String current = this.getValue();
                    int idx = poneys.indexOf(current);
                    int newIdx = (idx + steps) % poneys.size();
                    String newImg = poneys.get(newIdx);
                    this.setValue(newImg);
                }

        };

        // Default value for Spinner
        valueFactory.setValue(lama);

        this.spinnerCharacter.setValueFactory(valueFactory);


    }

    /**
     * Getter du spinner.
     * @return le spinner en question
     */
    public Spinner<String> getSpinner() {
        return this.spinnerCharacter;
    }

}
