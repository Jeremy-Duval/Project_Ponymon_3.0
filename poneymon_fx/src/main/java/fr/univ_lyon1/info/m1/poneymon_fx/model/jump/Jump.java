package fr.univ_lyon1.info.m1.poneymon_fx.model.jump;

/**
 * Interface pour les sauts.
 * 
 * @author Sylvain
 */
public interface Jump {
    double jump(JumpState jumpState, double jumpProgress);
}
