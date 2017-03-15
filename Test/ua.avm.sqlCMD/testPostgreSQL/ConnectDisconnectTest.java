package ua.avm.sqlCMD.testPostgreSQL;

import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.Disconnect;
import ua.avm.sqlCMD.model.DataBase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ConnectDisconnectTest extends TempPostgreSQL {
    private DataBase newConnect;
    @Test
    public void testConnect(){


        newConnect = new Connect(view, cmd.get("Command connect to the database."))
                .getDb(postgreSQL);
        assertTrue(newConnect.isConnect());
        new Disconnect(newConnect,view,cmd.get("Disconnecting from the server."))
                    .doIt(new String[] {"disconnect"});
        assertFalse(newConnect.isConnect());
    }
}
