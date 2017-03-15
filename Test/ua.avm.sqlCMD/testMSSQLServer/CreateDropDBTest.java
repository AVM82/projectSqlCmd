package ua.avm.sqlCMD.testMSSQLServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.CreateDB;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateDropDBTest extends TempMSSQLServer {

    @Before
    public void setup() {
        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(msServer);
    }

    @Test
    public void testCreateDropDB(){

        new CreateDB(db,view,cmd.get("Command creates a new database.")).doIt(new String[] {"",newDbName});
        assertTrue(db.isDataBaseExist(newDbName));
        db.dropDB(newDbName);
        assertFalse(db.isDataBaseExist(newDbName));
    }

    @After
    public void tear() {
        db.closeConnection();
    }
}
