package ua.avm.sqlCMD.testFireBird;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateDelTabTest extends TempFB{
    @Before
    public void setup() {
        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(fireBird);
    }

    @Test
    public void testCreateDropTabFireBird(){

        assertFalse(db.isTableExist(tableName));
        db.createTab(tableName, tableColumns);
        assertTrue(db.isTableExist(tableName));
        db.dropTable(tableName);
        assertFalse(db.isTableExist(tableName));
    }

    @After
    public void tear() {
        db.closeConnection();
    }

}
