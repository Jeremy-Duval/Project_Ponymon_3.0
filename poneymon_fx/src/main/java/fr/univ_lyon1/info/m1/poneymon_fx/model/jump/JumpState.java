package fr.univ_lyon1.info.m1.poneymon_fx.model.jump;

/**
 * Etats du saut du personnage.
 * 
 * @author Sylvain
 */
public enum JumpState {
    goDown("goDown"),
    goUp("goUp"),
    noJumping("noJumping");

    private final String name;

    JumpState(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(String other) {
        return this.toString().equals(other);
    }
}
