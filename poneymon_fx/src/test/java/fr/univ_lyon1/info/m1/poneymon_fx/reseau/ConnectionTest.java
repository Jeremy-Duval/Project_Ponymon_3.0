package fr.univ_lyon1.info.m1.poneymon_fx.reseau;


import fr.univ_lyon1.info.m1.poneymon_fx.App;
import fr.univ_lyon1.info.m1.poneymon_fx.model.AquaPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.GhostPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LamaModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ModeEnum;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import fr.univ_lyon1.info.m1.poneymon_fx.reseau.customer.Customer;
import fr.univ_lyon1.info.m1.poneymon_fx.reseau.host.Host;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;


/**
 * Test unitaire de la connexion entre le client et l'hôte
 */
public class ConnectionTest {
    private FieldModel fm;
    private FieldModel fm2;

    private Customer c;
    private Host h;

    private Host h1;
    private Customer c1;

    private Host h2;
    private Customer c2;
    private Customer c3;
    private Customer c4;
    private Host h5;
    private Customer c6;
    private Host h6;
    private Customer c7;
    private Customer c8;
    private Host h8;


    @Before
    public void setUp() throws IOException {
        Class[] cl = new Class[App.characterNumber];
        cl[0] = PoneyModel.class;
        cl[1] = LamaModel.class;
        cl[2] = AquaPoneyModel.class;
        cl[3] = GhostPoneyModel.class;
        cl[4] = PoneyModel.class;
        
        fm = new FieldModel(cl, 3, "test", ModeEnum.LOCAL, null);
        fm2 = new FieldModel(cl, 3, "test", ModeEnum.LOCAL, null);
        c = new Customer(InetAddress.getLocalHost(),1340,fm);
        c1 = new Customer(InetAddress.getLocalHost(), 1341, fm);
        c2 = new Customer(InetAddress.getLocalHost(), 1342, fm);
        c3 = new Customer(InetAddress.getLocalHost(), 1342, fm);
        c4 = new Customer(InetAddress.getLocalHost(), 1342, fm);
        c6 = new Customer(InetAddress.getLocalHost(), 1345, fm);
        c7 = new Customer(InetAddress.getLocalHost(), 1349, fm);
        c8 = new Customer(InetAddress.getLocalHost(), 1348, fm);
        c.setIpAtLocalAdress();
        c1.setIpAtLocalAdress();
        c2.setIpAtLocalAdress();
        c3.setIpAtLocalAdress();
        c4.setIpAtLocalAdress();
        c6.setIpAtLocalAdress();
        c7.setIpAtLocalAdress();
        c8.setIpAtLocalAdress();
        h = new Host(fm2,1340,1);
        h1 = new Host(fm2,1341,1);
        h2 = new Host(fm2,1342,1);
        h5 = new Host(fm2,1345,1);
        h6 = new Host(fm2,1349,1);
        h8 = new Host(fm2,1348,1);

    }

    @Test
    public void testInterface() {
        c1.getAnInterface().setReady(true);
        assertTrue(c1.getAnInterface().isReady());
    }

    @Test
    public void testConnect() throws InterruptedException {
        h.launch();
        c.launch();
        TimeUnit.SECONDS.sleep(1);
        c.setReady(true);
        h.getHc().getInter().get(0).setBeginning(true);
        h.getHc().getInter().get(0).setQuit(true);
        assertEquals(1, h.getHc().getInter().size());
    }

    @Test
    public void testConnectmultiple() throws InterruptedException {
        h2.launch();
        c2.launch();
        c3.launch();
        c4.launch();
        TimeUnit.SECONDS.sleep(1);
        c2.setReady(true);
        c3.setReady(true);
        c4.setReady(true);
        h2.getHc().getInter().get(0).setBeginning(true);
        h2.getHc().getInter().get(1).setBeginning(true);
        h2.getHc().getInter().get(2).setBeginning(true);
        h2.getHc().getInter().get(0).setQuit(true);
        h2.getHc().getInter().get(1).setQuit(true);
        h2.getHc().getInter().get(2).setQuit(true);
        assertEquals(3, h2.getHc().getInter().size());
    }



    @Test
    public void testConnectionInfo() throws InterruptedException {
        h1.launch();
        c1.launch();
        TimeUnit.SECONDS.sleep(1);
        c1.setReady(true);
        h1.getHc().getInter().get(0).setBeginning(true);
        h1.getHc().getInter().get(0).setQuit(true);
        assertEquals("Unknown", h1.getHc().getInter().get(0).getPseudo());

    }




    @Test
    public void testCustomerReady() throws InterruptedException {
        h5.launch();
        c6.launch();
        TimeUnit.SECONDS.sleep(1);
        c6.setReady(true);
        TimeUnit.SECONDS.sleep(1);
        assertTrue(h5.getHc().getInter().get(0).isReady());
        h5.getHc().getInter().get(0).setBeginning(true);
        h5.getHc().getInter().get(0).setQuit(true);
    }


    @Test
    public void testBeginning() throws InterruptedException {
        h6.launch();
        c7.launch();
        TimeUnit.SECONDS.sleep(1);
        c7.setReady(true);
        TimeUnit.SECONDS.sleep(1);
        h6.getHc().getInter().get(0).setBeginning(true);
        h6.getHc().getInter().get(0).setQuit(true);
        TimeUnit.SECONDS.sleep(1);
        assertEquals(h6.getHc().getInter().get(0).getModel().getWinner(),c7.getAnInterface().getModel().getWinner());
        // assert(h6.getHc().getInter().get(0).getModel().getCharacters() == c7.getAnInterface().getModel().getCharacters());
        assertEquals(h6.getHc().getInter().get(0).getModel().getNbLapToRun(),c7.getAnInterface().getModel().getNbLapToRun());
    }

    @Test
    public void testWinner() throws InterruptedException {
        h8.launch();
        c8.launch();
        TimeUnit.SECONDS.sleep(1);
        c8.setReady(true);
        TimeUnit.SECONDS.sleep(1);
        h8.getHc().getInter().get(0).setBeginning(true);
        h8.getHc().getInter().get(0).getModel().setWinner(h8.getHc().getInter().get(0).getModel().getCharacters()[0]);
        TimeUnit.SECONDS.sleep(1);
        assertTrue(c8.getAnInterface().getQuit());

    }

}