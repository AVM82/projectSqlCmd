package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.Disconnect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;


import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 01.11.2016.
 */

public class ConnectDisconnectTest {
    private View view;
    private DataBase db;
    private final String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private final String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private final String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");
    HashMap<String,String> cmd;

    @Before
    public void setup() {
        view = new Console();
        cmd = new Commands().getCMD();
    }

    @Test
    public void testConnectMSServer(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(msServer);
        assertTrue(db.isConnect());
        new Disconnect(db,view, cmd.get("Disconnecting from the server.")).doIt(new String[] {"disconnect"});
        assertFalse(db.isConnect());
    }

    @Test
    public void testConnectFireBird(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(fireBird);
        assertTrue(db.isConnect());
        new Disconnect(db,view, cmd.get("Disconnecting from the server.")).doIt(new String[] {"disconnect"});
        assertFalse(db.isConnect());
    }

    @Test
    public void testConnectPostgreSQL(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(postgreSQL);
        assertTrue(db.isConnect());
        new Disconnect(db,view, cmd.get("Disconnecting from the server.")).doIt(new String[] {"disconnect"});
        assertFalse(db.isConnect());
    }
}
