package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.App;
import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeremy
 */
public class FieldModelTest {
    private FieldModel fm;

    @Before
    public void setUp() {
        Class[] cl = new Class[App.characterNumber];
        cl[0] = PoneyModel.class;
        cl[1] = LamaModel.class;
        cl[2] = AquaPoneyModel.class;
        cl[3] = GhostPoneyModel.class;
        cl[4] = PoneyModel.class;
        
        fm = new FieldModel(cl, 3, "test", ModeEnum.LOCAL, null);
    }

    @Test
    public void testInitWinner() {
        assertNull(fm.getWinner());
    }
    
    @Test
    public void testGetPoneysSize(){
        assertEquals(App.characterNumber, fm.getCharacters().length);
    }

    @Test
    public void testSearchWinner(){
        CharacterModel[] characters = fm.getCharacters();
        CharacterModel c = characters[0];
        int currentLap;
        
        
        currentLap = c.getCurrentLap();

        while (currentLap < fm.getNbLapToRun()) {
            c.update();
            currentLap = c.getCurrentLap();
        }
        // Déclenche la mise à jour pour rechercher le gagnant
        fm.update();
        // Test si la couleur du gagnant est celle du poney voulu
        assertNotNull(fm.getWinner().getColor());
        assertEquals(c.getColor(), fm.getWinner().getColor());
    }
    
    @Test
    public void testNeighborForTheFirstPoney(){
        CharacterModel[] characters = fm.getCharacters();
        CharacterModel c = characters[0];
        
        // Test si le premier poney (à l'extrémité) à un voisin
        assertEquals(1, c.getNeighbors().size());
    }
    
    @Test
    public void testNeighborForTheSecondPoney(){
        CharacterModel[] characters = fm.getCharacters();
        CharacterModel c = characters[1];
        
        // Test si le deuxième poney à bien deux voisin
        assertEquals(2, c.getNeighbors().size());
    }
}
