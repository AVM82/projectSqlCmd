package ua.avm.sqlCMD.testMSSQLServer;

import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.Disconnect;
import ua.avm.sqlCMD.model.DataBase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ConnectDisconnectTest extends TempMSSQLServer {
    private DataBase newConnect;
    @Test
    public void testConnect(){


        newConnect = new Connect(view, cmd.get("Command connect to the database."))
                .getDb(msServer);
        assertTrue(newConnect.isConnect());
        new Disconnect(newConnect,view,cmd.get("Disconnecting from the server."))
                    .doIt(new String[] {"disconnect"});
        assertFalse(newConnect.isConnect());
    }
}
