package ua.avm.sqlCMD.testFireBird;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class ViewTab extends TempFB{
    private ArrayList<String[]> expected = new ArrayList<>();

    @Before
    public void setup() {
        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(fireBird);
        db.createTab(tableName,tableColumns);
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
    }


    @Test
    public void viewTableFireBird(){

        expected.add(new String[]{"NEW_FIELD1","NEW_FIELD2","NEW_FIELD3"});
        expected.add(new String[]{"33","value2","value3"});

        ArrayList<String[]> dataSet = db.viewTable(new String[] {"find",tableName});
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }
    }

    @After
    public void tear() {
        db.dropTable(tableName);
        db.closeConnection();
    }
}
