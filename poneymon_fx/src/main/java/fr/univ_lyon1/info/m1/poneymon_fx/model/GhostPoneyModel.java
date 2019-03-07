package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.BasicImmunToStun;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.WeakSlowDown;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.NormalJump;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.NetworkInterface;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public class GhostPoneyModel extends CharacterModel {
    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     * @param isAi  Spécifie si ce personnage est géré par une AI
     * @param mode local ou en ligne
     * @param net NetworkInterface
     */
    public GhostPoneyModel(ColorEnum color, int row, boolean isAi, 
            ModeEnum mode, NetworkInterface net) {
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
    public GhostPoneyModel(ColorEnum color, int row, ModeEnum mode, NetworkInterface net) {
        super(color, row, mode, net);
    }
    
    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     * @param isAi  Spécifie si ce personnage est géré par une AI
     */
    public GhostPoneyModel(ColorEnum color, int row, boolean isAi) {
        super(color, row, isAi, ModeEnum.LOCAL, null);
    }

    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     */
    public GhostPoneyModel(ColorEnum color, int row) {
        super(color, row);
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
        this.effectManagers.put(EffectStatusEnum.UNDEAD, new WeakSlowDown());
        this.addEffect(EffectStatusEnum.UNDEAD);
        this.jump = new NormalJump();
    }
}
