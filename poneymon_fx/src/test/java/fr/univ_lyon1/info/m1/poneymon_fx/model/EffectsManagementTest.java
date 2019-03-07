package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the EffectsManagement class.
 */
public class EffectsManagementTest {
    private LamaModel l;
    private PoneyModel p;

    @Before
    public void setUp() {
        l = new LamaModel(ColorEnum.GREEN, 0);
        p = new PoneyModel(ColorEnum.GREEN, 0);
    }
    
    @Test
    public void testStunLama() {
        double step;
        
        l.addEffect(EffectStatusEnum.STUN);
        step = l.computeStep();
        l.update();
        
        assertTrue((step > l.getStep()));
    }
    
    @Test
    public void testStunPoney() {
        double step;
        
        p.addEffect(EffectStatusEnum.STUN);
        step = p.computeStep();
        p.update();
        
        assertTrue((step > p.getStep()));
    }
    
    @Test
    public void testSlowDownLama() {
        double step;
        
        l.addEffect(EffectStatusEnum.AQUA_SLOW_DOWN);
        step = l.computeStep();
        l.update();
        
        assertTrue((step > l.getStep()));
    }
    
    @Test
    public void testSlowDownPoney() {
        double step;
        
        p.addEffect(EffectStatusEnum.AQUA_SLOW_DOWN);
        step = p.computeStep();
        p.update();
        
        assertTrue((step > p.getStep()));
    }
    
    @Test
    public void testImmunityToStun() {
        double step;
        
        p.addEffect(EffectStatusEnum.IMMUNITY_TO_STUN);
        p.addEffect(EffectStatusEnum.STUN);
        p.update();
        // on test si le stun est bien retiré
        assertTrue(!(p.isAffectedBy(EffectStatusEnum.STUN)));
    }
}
