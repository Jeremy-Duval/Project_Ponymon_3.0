package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.objectEffects.EffectStatusEnum;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Map functionnality.
 * @author jeremy
 */
public class MapTest {
    private Map m;

    @Before
    public void setUp() {
        m = new Map();
    }

    @Test
    public void testInitMap() {
        // Test si toute la map est à null
        for(int i = 0; i < m.getNbLine(); ++i){
            for(int j = 0; j < m.getNbLap(); ++j){
                for(int k = 0; k < m.getNbColumn(); ++k){
                    assertEquals(null, m.getObject(i, j, k));
                }
            }
        }
    }
    
    @Test
    public void testsetObject() {
        m.setObject(new ObjectObstacle(), 0, 0, 0);
        
        assertTrue(m.getObject(0, 0, 0) instanceof ObjectObstacle);
    }
    
    @Test
    public void testsetObjectToAllLine() {
        m.setObjectToAllLine(new ObjectPuddle(), 0, 0);
        
        for(int line = 0; line < m.getNbLine(); ++line){
            assertTrue(m.getObject(line, 0, 0) instanceof ObjectPuddle);
        }
    }
    
    @Test
    public void testAtMapSerializationIfATmpMapFileIsCreate() {
        Map tmp = new Map();
        // On sauvegarde la map et on test si elle tout s'est bien passé
        assertTrue(tmp.save("test"));
        // On test si la map esxiste
        assertTrue(Files.exists(Paths.get("map/test")));
    }
    
    @Test
    public void testSerialisationDeserialisationSameContents() {
        Map tmp, tmp2;
        
        tmp= new Map();
        
        tmp.setObjectToAllLine(new ObjectPuddle(), 0, 3);
        tmp.setObjectToAllLine(new ObjectObstacle(), 1, 3);
        
        
        tmp.save("test");
        tmp2 = Map.load("test");
        
        // test si chaque variable est identique pour les deux map
        assertEquals(tmp.getNbLine(), tmp2.getNbLine());
        assertEquals(tmp.getNbLap(), tmp2.getNbLap());
        assertEquals(tmp.getNbColumn(), tmp2.getNbColumn());
        // test si chaque cases des deux map sont identiques
        for(int i = 0; i < tmp2.getNbLine(); ++i){
            for(int j = 0; j < tmp2.getNbLap(); ++j){
                for(int k = 0; k < tmp2.getNbColumn(); ++k){
                    //on test si la case des deux map est null
                    if(tmp.getObject(i, j, k)==null){
                        assertTrue(tmp2.getObject(i, j, k) == null);
                    } else {
                        /* sinon, si la 1ère n'est pas null, mais si la deuxième
                        est null, alors elle sont différentes */
                        if(tmp2.getObject(i, j, k) == null){
                            assertTrue(false);
                        } else {
                            // sinon, on test si ce sont les mêmes objets dans la case
                            assertTrue(tmp.getObject(i, j, k).getClass() == tmp2.getObject(i, j, k).getClass());
                        }
                    }
                }
            }
        }
    }
    
    @Test
    public void testSerialisationDeserialisationFileNotFound() {
        Map tmp, tmp2;
        
        tmp= new Map();
        
        tmp.setObjectToAllLine(new ObjectPuddle(), 2, 3);
        
        
        tmp.save("test");
        // Test si le chargement a échouée (fichier n'existe pas)
        assertEquals(null, Map.load("test2qefseg12gvy"));
        
    }
    
    @Test
    public void testSerialisationRepositoryNotExist() {
        Map tmp, tmp2;
        
        tmp= new Map();
        
        tmp.setObjectToAllLine(new ObjectPuddle(), 2, 3);
        // Test si la sauvegarde a échouée 
        assertTrue(!tmp.save("repox215dsdsssd/test"));
        
    }
    
    @Test
    public void testgetNextObjectProgressBefore() {
        Map tmp, tmp2;
        
        tmp= new Map();
        
        tmp.setObjectToAllLine(new ObjectPuddle(), 0, 3);
        // Detecte t'on le prochain objet ?
        assertTrue(tmp.getNextObject(0, 0, 0) instanceof ObjectPuddle);
    }
    
    @Test
    public void testgetNextObjectProgressAfter() {
        Map tmp, tmp2;
        double step;
        
        tmp= new Map();
        
        tmp.setObjectToAllLine(new ObjectPuddle(), 0, 3);
        step = 1.0 / tmp.getNbColumn();
        // Si on est après l'objet, on ne doit pas le détecter
        assertEquals(null, tmp.getNextObject(0, 0, (step + 0.001) * 4));
    }
    
    @Test
    public void testgetNextObjectProgressNone() {
        Map tmp, tmp2;
        double step;
        
        tmp= new Map();
        
        // Si il n'y a pas d'objet ce tour, on attend null
        assertEquals(null, tmp.getNextObject(0, 0, 0));
    }
    
    @Test
    public void testgetNextObjectColumnProgressBefore() {
        Map tmp, tmp2;
        
        tmp= new Map();
        
        tmp.setObjectToAllLine(new ObjectPuddle(), 0, 3);
        // Detecte t'on le prochain objet ?
        assertEquals(3, tmp.getNextObjectColumn(0, 0, 0));
    }
    
    @Test
    public void testgetNextObjectColumnProgressAfter() {
        Map tmp, tmp2;
        double step;
        
        tmp= new Map();
        
        tmp.setObjectToAllLine(new ObjectPuddle(), 0, 3);
        step = 1.0 / tmp.getNbColumn();
        // Si on est après l'objet, on ne doit pas le détecter
        assertEquals(-1, tmp.getNextObjectColumn(0, 0, (step + 0.001) * 4));
    }
    
    @Test
    public void testgetNextObjectColumnProgressNone() {
        Map tmp, tmp2;
        double step;
        
        tmp= new Map();
        
        // Si il n'y a pas d'objet ce tour, on attend null
        assertEquals(-1, tmp.getNextObjectColumn(0, 0, 0));
    }
    
    @Test
    public void testApplyGoodEffectOnCharacIfCollisionDetected() {
        Class[] c = new Class[5];
        c[0] = PoneyModel.class;
        c[1] = PoneyModel.class;
        c[2] = PoneyModel.class;
        c[3] = PoneyModel.class;
        c[4] = PoneyModel.class;
        FieldModel fm = new FieldModel(c, 0, "jump", ModeEnum.LOCAL, null);
        
        CharacterModel[] charac = fm.getCharacters();
        while (charac[0].getProgress() < 0.35) {
            fm.update();
        }
        assertTrue(charac[0].isAffectedBy(EffectStatusEnum.AQUA_SLOW_DOWN));
    }
}
