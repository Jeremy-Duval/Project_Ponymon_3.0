package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public enum ColorEnum {
    BLUE("blue"),
    GREEN("green"),
    ORANGE("orange"),
    PURPLE("purple"),
    YELLOW("yellow");

    private final String color;

    /**
     * Constructeur de ColorEnum.
     *
     * @param color Valeur de la couleur
     */
    ColorEnum(String color) {
        this.color = color;
    }

    public String toString() {
        return color;
    }
}
