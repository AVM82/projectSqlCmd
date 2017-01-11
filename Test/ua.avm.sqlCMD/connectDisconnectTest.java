package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.Disconnect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 01.11.2016.
 */

public class connectDisconnectTest {
    private View view;
    private DataBase db;
    private String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");

    public connectDisconnectTest() throws Exception {
    }


    @Before
    public void setup() {
        view = new Console();
    }

    @Test
    public void testConnectMSServer(){

        db = new Connect(view).getDb(msServer);

            assertTrue(db.isConnect());
            new Disconnect(db,view).doIt(new String[] {"disconnect"});
            assertFalse(db.isConnect());

    }

    @Test
    public void testConnectFireBird(){

        db = new Connect(view).getDb(fireBird);

            assertTrue(db.isConnect());
            new Disconnect(db,view).doIt(new String[] {"disconnect"});
            assertFalse(db.isConnect());


    }

    @Test
    public void testConnectPostgreSQL(){

        db = new Connect(view).getDb(postgreSQL);

            assertTrue(db.isConnect());
            new Disconnect(db,view).doIt(new String[] {"disconnect"});
            assertFalse(db.isConnect());



    }
}
