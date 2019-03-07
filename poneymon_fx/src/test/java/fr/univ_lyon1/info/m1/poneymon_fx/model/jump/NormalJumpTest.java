package fr.univ_lyon1.info.m1.poneymon_fx.model.jump;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the Jump class.
 */
public class NormalJumpTest {
    Jump myJump = new NormalJump();
    
    @Before
    public void setUp() {
        this.myJump = new NormalJump();
    }
    
    @Test
    public void testJumpProgressIncreaseWhenCharacterGoUp() {
        assertEquals(0.05, myJump.jump(JumpState.goUp, 0.0), 0);
        assertEquals(0.77, myJump.jump(JumpState.goUp, 0.75), 0);
    }
    
    @Test
    public void testJumpProgressDecreaseWhenCharacterGoDown() {
        assertEquals(0.98, myJump.jump(JumpState.goDown, 1.0), 0);
        assertEquals(0.60, myJump.jump(JumpState.goDown, 0.65), 0);
    }
    
    @Test
    public void testJumpProgressNullWhenCharacterDontJump() {
        assertEquals(0.0, myJump.jump(JumpState.noJumping, 0.0), 0);
    }
}
