package ua.avm.sqlCMD.testMSSQLServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ListTabTest extends TempMSSQLServer {

    private Map<String, String> expected  = new HashMap<>();

    @Before
    public void setup() {
        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(msServer);
        db.createTab(tableName,tableColumns);
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
        expected.put("dbo."+tableName,"1");
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
