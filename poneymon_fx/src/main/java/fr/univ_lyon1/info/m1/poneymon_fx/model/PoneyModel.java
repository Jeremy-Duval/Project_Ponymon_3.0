package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.reseau.NetworkInterface;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.BasicImmunToStun;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.BigSlowDown;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.NormalBigBoost;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.WeakStun;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.NormalJump;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public class PoneyModel extends CharacterModel {
    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     * @param isAi  Spécifie si ce personnage est géré par une AI
     */
    public PoneyModel(ColorEnum color, int row, boolean isAi, ModeEnum mode, NetworkInterface net) {
        super(color, row, isAi, mode, net);
    }
    
    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     * @param mode local ou en ligne
     * @param net NetworkInterface
     */
    public PoneyModel(ColorEnum color, int row, ModeEnum mode, NetworkInterface net) {
        super(color, row, mode, net);
    }

    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     */
    public PoneyModel(ColorEnum color, int row) {
        super(color, row);
    }

    public PoneyModel(ColorEnum color, int row, boolean isAi) {
        super(color, row, isAi, ModeEnum.LOCAL, null);
    }

    @Override
    public double computeStep() {
        if (this.powered) {
            return super.computeStep() * 2.0;
        } else {
            return super.computeStep();
        }
    }
    
    @Override
    protected void init() {
        this.effectManagers.put(EffectStatusEnum.IMMUNITY_TO_STUN, new BasicImmunToStun());
        this.effectManagers.put(EffectStatusEnum.AQUA_SLOW_DOWN, new BigSlowDown());
        this.effectManagers.put(EffectStatusEnum.BOOST, new NormalBigBoost());
        this.effectManagers.put(EffectStatusEnum.STUN, new WeakStun());
        this.jump = new NormalJump();
    }
}
