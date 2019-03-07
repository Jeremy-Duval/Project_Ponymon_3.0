package fr.univ_lyon1.info.m1.poneymon_fx.model.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Victor Doucet doucet.victor@gmail.com
 */
public class AiFactory {
    /**
     * Instance unique de cette classe.
     */
    private static AiFactory instance;
    /**
     * Liste des stratégies possibles pour les personnages.
     */
    private final List<AiStrategy> strategies;

    /**
     * Constructeur de AiFactory, privé car on ne doit pas pouvoir créer
     * plusieurs instances.
     */
    private AiFactory() {
        AiFactory.instance = this;
        strategies = new ArrayList<AiStrategy>();
        strategies.add(new DumbAiStrategy());
        strategies.add(new NoJumpOptimizedAiStrategy());
        strategies.add(new PreciseJumpOptimizedAiStrategy());
        strategies.add(new PreciseWaterphilOptimizedAiStrategy());
        strategies.add(new SemiDumbAiStrategy());
        strategies.add(new WaterphilOptimizedAiStrategy());
        strategies.add(new SimpleAiStrategy());
        strategies.add(new OptimizedSimpleAiStrategy());
        strategies.add(new LogicAiStrategy());
        Collections.shuffle(strategies);
    }

    /**
     * Retourne une stratégie d'intelligence artificielle choisi au hasard.
     *
     * @return Une instance d'une intelligence artificielle
     */
    public static AiStrategy getAi() {
        if (AiFactory.instance == null) {
            new AiFactory();
        }
        return AiFactory.instance.nextAi();
    }

    /**
     * Effectue le mélange de la liste des intelligences et en retourne une.
     *
     * @return Une instance d'une intelligence artificielle
     */
    private AiStrategy nextAi() {
        Collections.shuffle(strategies);
        return strategies.get(0);
    }
}
