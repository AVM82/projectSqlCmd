package ua.avm.sqlCMD.testFireBird;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import java.util.ArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class InsertUpdateDeleteRowTest extends TempFB{


    private ArrayList<String[]> expected = new ArrayList<>();
    private ArrayList<String[]> dataSet;

    @Before
    public void setup() {

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(fireBird);
        db.createTab(tableName, tableColumns);

        expected.add(new String[]{"NEW_FIELD1","NEW_FIELD2","NEW_FIELD3"});
        expected.add(new String[]{"33","value2","value3"});
    }


    @Test
    public void testInsertUpdateDeleteRow(){
        //check insert
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
        dataSet = db.getDataByQuery("select * from "+tableName);
        checkData(expected, dataSet);

        //check update
        db.runQuery(db.buildUpdateQuery(updateRow.split(view.getSecondaryDelimiter()),tableName));
        expected.set(1,new String[]{"33","newValue","value3"} );
        dataSet = db.getDataByQuery("select * from "+tableName);
        checkData(expected, dataSet);

        //check delete
        db.runQuery(db.buildDeleteQuery(deleteRow.split("\\="),tableName));
        expected.remove(1);
        dataSet = db.getDataByQuery("select * from "+tableName);
        checkData(expected, dataSet);

    }

    private void checkData(ArrayList<String[]> expected, ArrayList<String[]> dataSet){

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
