package ua.avm.sqlCMD.testMSSQLServer;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ListDBTest extends TempMSSQLServer {
    private HashMap<String, String> expected = new HashMap<>();

    @Before
    public void setup() {
        expected.put("DRUG", "sa");
        expected.put("TEST", "sa");
        expected.put("ACINUS", "sa");
        expected.put("avm", "sa");
    }


    @Test
    public void testGetListDB(){

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(msServer);
        HashMap<String, String> listDB = db.getListDB();
        assertThat(listDB, is(expected));
        db.closeConnection();
    }
}
