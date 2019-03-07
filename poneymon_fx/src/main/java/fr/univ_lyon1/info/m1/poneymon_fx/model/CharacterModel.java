package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.Jump;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ai.AiFactory;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ai.AiStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.JumpState;
import fr.univ_lyon1.info.m1.poneymon_fx.model.effectsManagement.EffectsManagement;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;
import fr.univ_lyon1.info.m1.poneymon_fx.observer.UpdateObservable;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.NetworkInterface;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.customer.Customer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class contain data for the characters.
 *
 * @author jeremy
 */
public abstract class CharacterModel extends UpdateObservable implements Serializable {
    /**
     * Ligne sur laquelle évolue le personnage.
     */
    private int row;
    /**
     * Couleur du personnage.
     */
    private final ColorEnum color;
    private final AiStrategy ai;
    private final List<CharacterModel> neighbors;
    private String pseudo;
    /**
     * Indique si le personnage saute.
     */
    private JumpState jumpState = JumpState.noJumping;
    /**
     * Progrès du saut du personnage (compris entre 0.0 et 1.0).
     */
    private double jumpProgress = 0.0;
    protected Jump jump;

    /**
     * Nombre d'utilisation du pouvoir restant.
     */
    int remainingPowers = 3;
    /**
     * Vrai si le personnage est actuellement en train d'utiliser son pouvoir.
     */
    boolean powered = false;
    /**
     * Vrai si le personnage ne peut pas utiliser son pouvoir (ex : à cause d'effets).
     */
    private boolean powerIsBlock = false;
    /**
     * Progrès du personnage sur sa rangée (compris entre 0.0 et 1.0).
     */
    private double progress = 0.0;
    /**
     * Vitesse actuelle du personnage.
     */
    private double speed;
    /**
     * Pas de progression du personnage.
     */
    private double step;
    /**
     * Numéro du tour que le personnage est en train de courir.
     */
    private int currentLap = 0;
    /**
     * Liste contenant les différents effets affectant le poney.
     */
    private EnumSet<EffectStatusEnum> effectStatus;
    /**
     * Liste contenant les manières de gérer les effets que peut subir le personnage.
     */
    protected ConcurrentHashMap<EffectStatusEnum, EffectsManagement> effectManagers;
    /**
     * Prochain objet sur la map (champ de vision du perso).
     */
    private MapObject nextObjectOnMap;
    /**
     * Distance (progression entre 0 et 1) entre le perso et le prochain objet.
     */
    private double distanceBtwObj;

    private ModeEnum mode;

    private transient NetworkInterface net;

    private Lock updateLock;

    /**
     * Constructeur du CharacterModelInfo.
     *
     * @param color Couleur du personnage
     * @param row   Rangée sur laquelle évolue le personnage
     */
    public CharacterModel(ColorEnum color, int row, boolean isAi,
            ModeEnum mode,NetworkInterface net) {
        effectStatus = EnumSet.noneOf(EffectStatusEnum.class);
        effectManagers = new ConcurrentHashMap<EffectStatusEnum, EffectsManagement>();
        
        this.init();
        
        this.row = row;
        this.color = color;

        setRandomSpeed();
        neighbors = new ArrayList<CharacterModel>();

        if (isAi) {
            this.ai = AiFactory.getAi();
        } else {
            this.ai = null;
        }

        this.mode = mode;
        this.net = net;
        this.updateLock = new ReentrantLock();
    }

    public CharacterModel(ColorEnum color, int row) {
        this(color, row, false,ModeEnum.LOCAL,null);
    }
    
    /**
     * Constructeur du PoneyModel.
     *
     * @param color Couleur du poney
     * @param row   Rangée sur laquelle évolue le poney
     * @param mode local ou en ligne
     * @param net NetworkInterface
     */
    public CharacterModel(ColorEnum color, int row, ModeEnum mode, NetworkInterface net) {
        this(color, row, false, mode, net);
    }
    
    /**
     * Initialisation des managers d'effet, saut, nb de boost...
     */
    protected void init() {}


    /**
     * Change la vitesse du personnage aléatoirement.
     */
    private void setRandomSpeed() {
        Random randomGenerator = new Random();
        this.speed = 1.0f - randomGenerator.nextFloat();
        if (this.speed < 0.3f) {
            this.speed = 0.3f;
        }

    }
    
    /**
     * Renvoie la ligne sur laquelle évolue le personnage.
     *
     * @return Row on which the character is placed
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Renvoie l'avancement du personnage dans un tour (compris entre 0.0 et
     * 1.0).
     *
     * @return Progress of the personnage on the lap
     */
    public double getProgress() {
        return this.progress;
    }
    
    /**
     * Renvoie la progression du saut du personnage (compris entre 0.0 et
     * 1.0).
     *
     * @return Progress of the jump of the personnage
     */
    public double getJumpProgress() {
        return this.jumpProgress;
    }

    /**
     * Renvoie la couleur du personnage.
     *
     * @return couleur du personnage
     */
    public ColorEnum getColor() {
        return this.color;
    }

    /**
     * Calcul le pas que le poney doit avancer pendant un tour selon sa
     * vitesse et les différents multiplicateurs.
     *
     * @return Pas que le poney doit avancer ce tour
     */
    public double computeStep() {
        /*
        * Multiplieur pour la vitesse horizontal pour changer la vitesse du jeu.
        */
        double stepSize = 0.002;
        return stepSize * this.speed;
    }

    /**
     * Deplacement du personnage.
     * 
     * @return true si la position a changée
     */
    public boolean update() {
        synchronized (updateLock) {
            step = this.computeStep();

            // traitement des effets (SlowDown, Stun...)
            for (EffectStatusEnum e : this.effectStatus) {
                if (this.effectManagers.containsKey(e)) {
                    this.effectManagers.get(e).effectsManager(this);
                }
            }

            this.progress += step;
            if (this.progress > 1) {
                this.progress = 0;
                ++this.currentLap;
                setRandomSpeed();
                this.finishLap();
                this.notifyObservers();
            }
            if (this.ai != null) {
                this.ai.run(this);
            }
            this.updateJump();
            return step != 0;
        }
    }
    
    private void updateJump() {
        synchronized (updateLock) {
            this.jumpProgress = this.jump.jump(this.jumpState, this.jumpProgress);
            if (this.jumpState.equals(JumpState.goUp) && this.jumpProgress >= 1.0) {
                this.jumpState = JumpState.goDown;
            } else if (this.jumpState.equals(JumpState.goDown) && this.jumpProgress <= 0.0) {
                this.jumpState = JumpState.noJumping;
                this.jumpProgress = 0.0;
            }
        }
    }


    /**
     * Renvoie le tour que le personnage est en train de parcourir.
     *
     * @return Current lap of the character
     */
    public int getCurrentLap() {
        return this.currentLap;
    }

    /**
     * Vrai si le personnage est en train d'utiliser son pouvoir.
     *
     * @return True if this character is using their power
     */
    public boolean isPowered() {
        return this.powered;
    }

    /**
     * Nombre d'utilisation du pouvoir restant.
     *
     * @return Number of remaining use for the power
     */
    public int getRemainingPower() {
        return this.remainingPowers;
    }

    /**
     * Utilise le pouvoir ou le redirige vers l hote de la partie suivant le mode de l'application.
     */
    public void tryUsePower() {
        if (mode == ModeEnum.LOCAL) {
            this.usePower();
        }
        if (mode == ModeEnum.HOST && row == 0) {
            this.usePower();
        }
        if (mode == ModeEnum.CUSTOMER) {
            ((Customer)net).setPower();
        }
    }

    /**
     * Appelé quand on essaye de déclencher le pouvoir du personnage.
     */
    public void usePower() {
        synchronized (updateLock) {
            if (this.remainingPowers > 0 && !this.isPowered() && !this.powerIsBlock) {
                this.powered = true;
                --this.remainingPowers;
                this.notifyObservers();
            }
        }
    }

    /**
     * Utilise le saut ou le redirige vers l hote de la partie suivant le mode de l'application.
     */
    public void tryUseJump() {
        if (mode == ModeEnum.LOCAL) {
            this.useJump();
        }
        if (mode == ModeEnum.HOST && row == 0) {
            this.useJump();
        }
        if (mode == ModeEnum.CUSTOMER) {
            ((Customer)net).setJump();
        }

    }

    /**
     * Appelée à la fin de chaque tour pour réinitialiser certaines variables
     * si besoin.
     */
    void finishLap() {
        this.powered = false;
    }


    public void addNeighbor(CharacterModel model) {
        this.neighbors.add(model);
    }

    public List<CharacterModel> getNeighbors() {
        return this.neighbors;
    }
    
    public Jump getJump() {
        return this.jump;
    }
    
    public JumpState getJumpState() {
        return this.jumpState;
    }

    /**
     * Appelé quand on essaye de déclencher le saut du personnage.
     */
    public void useJump() {
        synchronized (updateLock) {
            if (this.jumpState.equals(JumpState.noJumping)) {
                this.jumpState = JumpState.goUp;
            }
        }
    }

    

    public double distance(CharacterModel other) {
        return other.currentLap + other.progress - this.currentLap - this.progress;
    }

    public boolean isAi() {
        return this.ai != null;
    }

    public  void setRow(int row) {
        this.row = row;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    /**
     * Copie partiellement le character mis en paramètre.
     */
    public void updateChar(CharacterModel character) {
        synchronized (updateLock) {
            this.progress = character.progress;
            this.jumpProgress = character.jumpProgress;

            this.powered = character.powered;
            this.powerIsBlock = character.powerIsBlock;
            this.remainingPowers = character.remainingPowers;

            this.currentLap = character.currentLap;
            this.speed = character.speed;
            this.effectStatus = character.effectStatus;

            this.notifyObservers();
        }
    }
    
    /**
     * Sert à savoir si le personnage est affecté par l'effet en paramètre. 
     * 
     * @param effect effet pour lequel on effectu le test
     * @return true si le personnage est affecté par cet effet, false sinon
     */
    public boolean isAffectedBy(EffectStatusEnum effect) {
        return this.effectStatus.contains(effect);
    }

    /**
     * Affecte un effet au personnage.
     * 
     * @param effect effet à affecté au personnage
     */
    public void addEffect(EffectStatusEnum effect) {
        this.effectStatus.add(effect);
    }
    
    /**
     * Renvoie la liste des effets affectant le personnage.
     * 
     * @return liste des effets affectant le personnage
     */
    public EnumSet getEffects() {
        return this.effectStatus;
    }
    
    /**
     * Test si le personnage n'est affecté par aucun effet.
     * 
     * @return true si aucun effet affecte le personnage, false sinon 
     */
    public boolean hasNoEffects() {
        return this.effectStatus.isEmpty();
    }
    
    /**
     * Retire un effet affecté au poney.
     * 
     * @param effect effet à retiré
     * @return true si l'élément à été supprimé
     */
    public boolean removeEffect(EffectStatusEnum effect) {
        return this.effectStatus.remove(effect);
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public void setPowerIsBlock(boolean powerIsBlock) {
        this.powerIsBlock = powerIsBlock;
    }
    
    /**
     * Met à jour le champ de vision du personnage.
     * @param nextObj le prochain objet devant le personnage
     * @param objPosition la progression (entre 0 et 1) à laquelle se situe l'objet.
     */
    public void updateVision(MapObject nextObj, double objPosition) {
        this.nextObjectOnMap = nextObj;
        this.distanceBtwObj = objPosition - this.progress;
    }

    public MapObject getNextObjectOnMap() {
        return nextObjectOnMap;
    }

    public double getDistanceBtwObj() {
        return distanceBtwObj;
    }

    public void setNet(NetworkInterface netI) {
        this.net = netI;
    }

    public void setMode(ModeEnum mode) {
        this.mode = mode;
    }
}
