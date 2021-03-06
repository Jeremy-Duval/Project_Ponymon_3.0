package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.NetworkInterface;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.BasicImmunToStun;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.BigStun;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.NormalWeakBoost;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.WeakSlowDown;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.LongerJump;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public class LamaModel extends CharacterModel {
    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     * @param isAi  Spécifie si ce personnage est géré par une AI
     */
    public LamaModel(ColorEnum color, int row, boolean isAi, ModeEnum mode, NetworkInterface net) {
        super(color, row, isAi, mode, net);
    }
    
    /**
     * Constructeur du LamaModel.
     *
     * @param color Couleur du personnage
     * @param row   Rangée sur laquelle évolue le personnage
     * @param isAi  Spécifie si ce personnage est géré par une AI
     */
    public LamaModel(ColorEnum color, int row, boolean isAi) {
        super(color, row, isAi, ModeEnum.LOCAL, null);
    }
    
    /**
     * Constructeur du LamaModel.
     *
     * @param color Couleur du lama
     * @param row   Rangée sur laquelle évolue le lama
     */
    public LamaModel(ColorEnum color, int row) {
        super(color, row);
    }
    
    /**
     * Constructeur du LamaModel.
     *
     * @param color Couleur du personnage
     * @param row   Rangée sur laquelle évolue le personnage
     * @param mode local ou en ligne
     * @param net NetworkInterface
     */
    public LamaModel(ColorEnum color, int row, ModeEnum mode, NetworkInterface net) {
        super(color, row, mode, net);
    }

    @Override
    public double computeStep() {
        if (this.powered) {
            return super.computeStep() * 3.0;
        } else {
            return super.computeStep();
        }
    }
    
    @Override
    protected void init() {
        this.effectManagers.put(EffectStatusEnum.IMMUNITY_TO_STUN, new BasicImmunToStun());
        this.effectManagers.put(EffectStatusEnum.AQUA_SLOW_DOWN, new WeakSlowDown());
        this.effectManagers.put(EffectStatusEnum.BOOST, new NormalWeakBoost());
        this.effectManagers.put(EffectStatusEnum.STUN, new BigStun());
        this.remainingPowers = 2;
        this.jump = new LongerJump();
    }
}
