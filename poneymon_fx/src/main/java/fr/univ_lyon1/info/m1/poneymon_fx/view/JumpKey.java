package fr.univ_lyon1.info.m1.poneymon_fx.view;

/**
 * Touches de jump.
 * 
 * @author Sylvain
 */
public enum JumpKey {
    A("A"),
    P("P"),
    G("G"),
    N1("DIGIT1"),
    N8("DIGIT8");

    private final String name;

    JumpKey(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(String other) {
        return this.toString().equals(other);
    }
}
