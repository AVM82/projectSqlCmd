package ua.avm.sqlCMD.testFireBird;

import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import java.util.HashMap;
import static org.junit.Assert.assertNull;

public class ListDBTest extends  TempFB{

    @Test
    public void testGetListDBFireBird(){

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(fireBird);
        HashMap<String, String> listDB = db.getListDB();
        assertNull(listDB);
        db.closeConnection();
    }
}
