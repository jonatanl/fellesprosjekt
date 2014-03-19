package tests;

import interfaces.PersistencyInterface;
import junit.framework.TestCase;
import util.Persistency;

public class LoginTest extends TestCase{

    public void testRequestLogin() throws Exception {
        PersistencyInterface persistency = new Persistency();
        int id = persistency.requestLogin("jonatan", "123456");
        assertTrue("Request login", id > 0);
    }
}
