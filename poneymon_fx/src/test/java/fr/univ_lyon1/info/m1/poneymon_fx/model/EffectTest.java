package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.NormalJump;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.AquaSlowDownEffect;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.StunEffect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jeremy
 */
public class EffectTest {
    private PoneyModel p;

    @Before
    public void setUp() {
        p = new PoneyModel(ColorEnum.GREEN, 0);
    }
    
    @Test
    public void testEffectStun() {
        StunEffect sE = new StunEffect();
        
        sE.applyEffect(p);
        
        Assert.assertTrue(p.isAffectedBy(EffectStatusEnum.STUN));
    }
    
    @Test
    public void testEffectSlowDown() {
        AquaSlowDownEffect s = new AquaSlowDownEffect();
        
        s.applyEffect(p);
        
        Assert.assertTrue(p.isAffectedBy(EffectStatusEnum.AQUA_SLOW_DOWN));
    }
    
}
