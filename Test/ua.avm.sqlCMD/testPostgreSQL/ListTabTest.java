package ua.avm.sqlCMD.testPostgreSQL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ListTabTest extends TempPostgreSQL {

    private Map<String, String> expected  = new HashMap<>();

    @Before
    public void setup() {
        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(postgreSQL);
        db.createTab(tableName,tableColumns);
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
        expected.put(tableName,"1");
        //expected.put("user","3");
    }

    @Test
    public void testGetListTab(){

        Map<String, String> listTab = db.getListTable();
        assertThat(listTab, is(expected));
    }

    @After
    public void tear() {
        db.dropTable(tableName);
        db.closeConnection();
    }

}
