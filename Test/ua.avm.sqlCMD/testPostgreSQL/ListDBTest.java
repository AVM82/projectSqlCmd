package ua.avm.sqlCMD.testPostgreSQL;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ListDBTest extends TempPostgreSQL {
    private HashMap<String, String> expected = new HashMap<>();

    @Before
    public void setup() {
        expected.put("test", "postgres");
        expected.put("postgres", "postgres");

    }


    @Test
    public void testGetListDB(){

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(postgreSQL);
        HashMap<String, String> listDB = db.getListDB();
        assertThat(listDB, is(expected));
        db.closeConnection();
    }
}
