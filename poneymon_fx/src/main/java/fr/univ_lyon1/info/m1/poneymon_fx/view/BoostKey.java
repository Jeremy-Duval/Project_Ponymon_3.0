package fr.univ_lyon1.info.m1.poneymon_fx.view;

/**
 * Touches de usePower.
 *
 * @author jeremy
 */
public enum BoostKey {
    Q("Q"),
    M("M"),
    B("B"),
    N2("DIGIT2"),
    N9("DIGIT9");

    private final String name;

    BoostKey(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(String other) {
        return this.toString().equals(other);
    }
}
