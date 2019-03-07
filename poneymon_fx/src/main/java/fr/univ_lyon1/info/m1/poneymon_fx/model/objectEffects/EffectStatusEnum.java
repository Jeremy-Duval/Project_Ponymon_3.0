package fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects;

/**
 * Les différents statuts que les effets peuvent infligés aux poneys.
 * @author jeremy
 */
public enum EffectStatusEnum implements Comparable<EffectStatusEnum> {
    AQUA_SLOW_DOWN("aqua_slow_down"),
    BOOST("boost"),
    IMMUNITY_TO_STUN("immunity_to_stun"),
    STUN("stun"),
    UNDEAD("undead");

    private final String status;

    /**
     * Constructeur de ColorEnum.
     *
     * @param color Valeur de la couleur
     */
    EffectStatusEnum(String status) {
        this.status = status;
    }

    public String toString() {
        return status;
    }
}
