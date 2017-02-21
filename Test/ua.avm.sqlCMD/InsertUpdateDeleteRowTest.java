package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 07.02.2017.
 */
public class InsertUpdateDeleteRowTest {


    private View view;
    private DataBase db;
    private final String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private final String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private final String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");



    private ArrayList<String[]> expected = new ArrayList<>();
    private ArrayList<String[]> dataSet;
    HashMap<String,String> cmd;

    @Before
    public void setup() {

        view = new Console();
        cmd = new Commands().getCMD();
    }

    @Test
    public void testForPostgreSQL(){
        expected.clear();
        String tableName = "user";
        String insertRow = "name=value1|password=value2";
        String updateRow = "name=newValue|name=value1";
        String deleteRow = "name=newValue";

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(postgreSQL);

        //check insert
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
        expected.add(new String[]{"name","password"});
        expected.add(new String[]{"value1","value2"});
        dataSet = db.getDataByQuery("select * from public.user where name = 'value1'");
        checkData(expected, dataSet);

        //check update
        db.runQuery(db.buildUpdateQuery(updateRow.split(view.getSecondaryDelimiter()),tableName));
        expected.set(1,new String[]{"newValue","value2"} );
        dataSet = db.getDataByQuery("select * from public.user where name = 'newValue'");
        checkData(expected, dataSet);

        //check delete
        db.runQuery(db.buildDeleteQuery(deleteRow.split("\\="),tableName));
        expected.remove(1);
        dataSet = db.getDataByQuery("select * from public.user where name = 'newValue'");
        checkData(expected, dataSet);

    }

    @Test
    public void testForMSServer(){
        expected.clear();
        String tableName = "LP";
        String insertRow = "id=2|name=value1|password=value2";
        String updateRow = "name=newValue|name=value1";
        String deleteRow = "name=newValue";

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(msServer);

        //check insert
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
        expected.add(new String[]{"id","name","password"});
        expected.add(new String[]{"2","value1","value2"});
        dataSet = db.getDataByQuery("use avm select * from dbo.LP where name = 'value1'");
        checkData(expected, dataSet);

        //check update
        db.runQuery(db.buildUpdateQuery(updateRow.split(view.getSecondaryDelimiter()),tableName));
        expected.set(1,new String[]{"2","newValue","value2"} );
        dataSet = db.getDataByQuery("use avm select * from dbo.LP where name = 'newValue'");
        checkData(expected, dataSet);

        //check delete
        db.runQuery(db.buildDeleteQuery(deleteRow.split("\\="),tableName));
        expected.remove(1);
        dataSet = db.getDataByQuery("use avm select * from dbo.LP where name = 'newValue'");
        checkData(expected, dataSet);

    }

    @Test
    public void testForFireBird(){
        expected.clear();
        String tableName = "A_SYS";
        String insertRow = "A_SYS=value1|A_SYS_VALUE=value2";
        String updateRow = "A_SYS=newValue|A_SYS=value1";
        String deleteRow = "A_SYS=newValue";

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(fireBird);

        //check insert
        db.runQuery(db.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),tableName));
        expected.add(new String[]{"A_SYS","A_SYS_VALUE"});
        expected.add(new String[]{"value1","value2"});
        dataSet = db.getDataByQuery("select * from A_SYS where A_SYS = 'value1'");
        checkData(expected, dataSet);

        //check update
        db.runQuery(db.buildUpdateQuery(updateRow.split(view.getSecondaryDelimiter()),tableName));
        expected.set(1,new String[]{"newValue","value2"} );
        dataSet = db.getDataByQuery("select * from A_SYS where A_SYS = 'newValue'");
        checkData(expected, dataSet);

        //check delete
        db.runQuery(db.buildDeleteQuery(deleteRow.split("\\="),tableName));
        expected.remove(1);
        dataSet = db.getDataByQuery("select * from A_SYS where A_SYS = 'newValue'");
        checkData(expected, dataSet);

    }

    void checkData(ArrayList<String[]> expected, ArrayList<String[]> dataSet){

        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }

    }



}
