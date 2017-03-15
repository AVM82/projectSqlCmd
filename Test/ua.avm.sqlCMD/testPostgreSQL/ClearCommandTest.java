package ua.avm.sqlCMD.testPostgreSQL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TempPostgreSQL {


    @Before
    public void setup() {
        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(postgreSQL);
        db.createTab(tableName,tableColumns);
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
    }

    @Test
    public void testForClearCommand(){
        ArrayList<String[]> expected = new ArrayList<>();
        ArrayList<String[]> dataSet = db.viewTable(new String[] {"view",tableName});
        expected.add(new String[]{"new_field1","new_field2","new_field3"});
        expected.add(new String[]{"33","value2","value3"});
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < expected.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }
        db.runQuery(db.buildClearTabQuery(tableName));
        expected.remove(1);
        dataSet = db.viewTable(new String[] {"view",tableName});
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < expected.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }
    }
    @After
    public void tear() {
        db.dropTable(tableName);
        db.closeConnection();
    }

}
