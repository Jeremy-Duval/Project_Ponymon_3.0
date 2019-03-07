package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.JumpState;
import fr.univ_lyon1.info.m1.poneymon_fx.model.jump.NormalJump;
import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;
import org.junit.Before;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for the Poney class.
 */
public class PoneyModelTest {
    private PoneyModel p;

    @Before
    public void setUp() {
        p = new PoneyModel(ColorEnum.GREEN, 0);
    }

    @Test
    public void testConstuctorParametersColorAndRow() {
        assertEquals(ColorEnum.GREEN, p.getColor());
        assertEquals(p.getRow(), 0);
    }
    
    @Test
    public void testConstuctorInitEffects() {
        assertTrue(p.hasNoEffects());
    }

    @Test
    public void testStepProgressIncrase() {
        p.update();

        assertTrue((p.getProgress() > 0));
    }

    @Test
    public void testStepProgressReinitAfter100AndNewRow() {
        double stepSizeMultSpeed;
        int lapBefore, lapAfter;

        lapBefore = p.getCurrentLap();

        p.update();
        stepSizeMultSpeed = p.getProgress();

        while (p.getProgress() < (1.0 - stepSizeMultSpeed)) {
            p.update();
        }

        // on augmente deux fois pour dépasser 1.0
        p.update();
        p.update();

        lapAfter = p.getCurrentLap();

        // est-on repassé en dessous de 1.0 (=a t-on commencé un nouveau tour) ?
        assertTrue(p.getProgress() < 1.0);
        // a t-on changé de tour ?
        assertTrue(lapBefore < lapAfter);
    }

    @Test
    public void testLapInit() {
        assertEquals(0, p.getCurrentLap());
    }

    @Test
    public void testPower() {
        int initRemaingBoost = p.getRemainingPower();

        assertFalse(p.isPowered());
        p.usePower();

        // test si la variable powered a été mise à jour
        assertTrue(p.isPowered());

        // test si le nombre de usePower restant a diminué
        assertTrue(p.getRemainingPower() < initRemaingBoost);
    }

    @Test
    public void testSpeedIncreaseWhenPoneyIsBoosted() {
        double speedBeforeBoost;

        speedBeforeBoost = p.computeStep();

        p.usePower();

        // Test si la vitesse a bien été augmentée après le usePower
        assertTrue(speedBeforeBoost < p.computeStep());
    }

    @Test
    public void testFinishLapReinitBoosted() {
        p.usePower();

        assertTrue(p.isPowered());

        p.finishLap();

        // Test si la variable boosted a été mise à jour (remise à false)
        assertFalse(p.isPowered());
    }

    @Test
    public void testPowerIsNotIllimited() {
        int initialPowers = p.getRemainingPower();
        for (int i = 0; i < initialPowers; ++i) {
            p.usePower();
            p.update();
            while (p.getProgress() != 0) {
                p.update();
            }
        }

        assertFalse(p.isPowered());
        assertEquals(0, p.remainingPowers);
        p.usePower();
        assertFalse(p.isPowered());
    }
    
    @Test
    public void testAddNeighbor() {
        PoneyModel p2 = new PoneyModel(ColorEnum.BLUE, 0);
        
        p.addNeighbor(p2);
        // test si p2 a bien été ajouté à p comme voisin
        assertEquals(p2, p.getNeighbors().get(0));
    }
    
    @Test
    public void testJumpStateChangeWhenJumpProgressReachMaximumValue() {
        p.useJump();
        p.update();
        while (p.getJumpProgress() < 1.0) {
            assertTrue(p.getJumpState().equals(JumpState.goUp));
            p.update();
        }
        
        while (p.getJumpProgress() > 0.0) {
            assertTrue(p.getJumpState().equals(JumpState.goDown));
            p.update();
        }
        assertTrue(p.getJumpState().equals(JumpState.noJumping));
    }
    
    @Test
    public void testJumpStateIsChangeIfWasntAlreadyJumping() {
        p.useJump();
        assertEquals(JumpState.goUp, p.getJumpState());
    }

    @Test
    public void testAddEffect() {
        p.addEffect(EffectStatusEnum.STUN);
        
        assertTrue(!p.hasNoEffects());
    }
    
    @Test
    public void testIsAffectedByStun() {
        p.addEffect(EffectStatusEnum.STUN);
        
        assertTrue(p.isAffectedBy(EffectStatusEnum.STUN));
    }
    
    @Test
    public void testRemoveStun() {
        p.addEffect(EffectStatusEnum.STUN);
        p.removeEffect(EffectStatusEnum.STUN);
        
        assertTrue(!p.isAffectedBy(EffectStatusEnum.STUN));
    }
    
    @Test
    public void testGetStep() {
        p.update();
        
        assertEquals(p.computeStep(), p.getStep(), 0.000001);
    }
    
    @Test
    public void testSetStep() {
        p.update();
        double step = p.getStep();
        p.setStep(step * 0.5);
        
        assertEquals(step * 0.5, p.getStep(), 0.000001);
    }
}
