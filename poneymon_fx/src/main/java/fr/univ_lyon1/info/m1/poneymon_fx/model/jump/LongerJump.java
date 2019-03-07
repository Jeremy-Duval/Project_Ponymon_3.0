package fr.univ_lyon1.info.m1.poneymon_fx.model.jump;

import java.io.Serializable;

/**
 * Effectue un saut qui dure plus longtemps que les autres.
 * 
 * @author Sylvain
 */
public class LongerJump implements Jump, Serializable {

    /**
     * Ajuste la progression du saut en fonction de son etat.
     * 
     * @param jumpState etat actuel du saut du personnage
     * @param jumpProgress progression actuelle du saut du personnage
     * @return nouvelle progression du saut du personnage
     */
    public double jump(JumpState jumpState, double jumpProgress) {
        switch (jumpState) {
            case goUp:
                if (jumpProgress > 0.7) {
                    return jumpProgress + 0.01;
                } else {
                    return jumpProgress + 0.02;
                }
            case goDown:
                if (jumpProgress > 0.7) {
                    return jumpProgress - 0.01;
                } else {
                    return jumpProgress - 0.02;
                }
            case noJumping:
            default:
                return jumpProgress;
        }
    }
}
