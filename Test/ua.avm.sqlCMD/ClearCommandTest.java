package ua.avm.sqlCMD;

import org.junit.*;
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
 * Created by AVM on 21.02.2017.
 */
public class ClearCommandTest {

    private static View view;
    private static DataBase postgreSQLDB;
    private static DataBase msServerDB;
    private static DataBase fireBirdDB;
    private static final String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private static final String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private static final String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");
    private static final String[][] columns = {
            {"new_field1","integer","y","n"},
            {"new_field2","varchar(20)","n","n"},
            {"new_field3","varchar(10)","n","y"}
    };
    static HashMap<String,String> cmd;
    private ArrayList<String[]> tableColumns = new ArrayList<>();

    @BeforeClass
    public static void fSetup(){

        view = new Console();
        cmd = new Commands().getCMD();
        postgreSQLDB = new Connect(view, cmd.get("Command connect to the database.")).getDb(postgreSQL);
        msServerDB = new Connect(view, cmd.get("Command connect to the database.")).getDb(msServer);
        fireBirdDB = new Connect(view, cmd.get("Command connect to the database.")).getDb(fireBird);
    }
    @Before
    public void setup() {

        tableColumns.add(columns[0]);
        tableColumns.add(columns[1]);
        tableColumns.add(columns[2]);


        String insertRow = "new_field1=33|new_field2=value2|new_field3=value3";

        postgreSQLDB.createTab("new_tab",tableColumns);
        postgreSQLDB.runQuery(postgreSQLDB.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),"new_tab"));

        msServerDB.createTab("new_tab",tableColumns);
        msServerDB.runQuery(msServerDB.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),"new_tab"));

        fireBirdDB.createTab("NEW_TAB",tableColumns);
        fireBirdDB.runQuery(fireBirdDB.buildInsertQuery(insertRow.split(view.getSecondaryDelimiter()),"NEW_TAB"));


    }

    @Test
    public void testForClearCommand(){


        ArrayList<String[]> expected = new ArrayList<>();
        ArrayList<String[]> expectedFB = new ArrayList<>();
        ArrayList<String[]> dataSet1 = postgreSQLDB.viewTable(new String[] {"view","new_tab"});
        ArrayList<String[]> dataSet2 = msServerDB.viewTable(new String[] {"view","new_tab"});
        ArrayList<String[]> dataSet3 = fireBirdDB.viewTable(new String[] {"view","NEW_TAB"});

        expected.add(new String[]{"new_field1","new_field2","new_field3"});
        expectedFB.add(new String[]{"NEW_FIELD1","NEW_FIELD2","NEW_FIELD3"});
        expected.add(new String[]{"33","value2","value3"});
        expectedFB.add(new String[]{"33","value2","value3"});

        assertTrue(expected.size() == dataSet1.size());
        assertTrue(expected.size() == dataSet2.size());
        assertTrue(expected.size() == dataSet3.size());

        for(int i = 0; i < expected.size(); i++){
            assertThat(dataSet1.get(i), is(expected.get(i)));
            assertThat(dataSet2.get(i), is(expected.get(i)));
            assertThat(dataSet3.get(i), is(expectedFB.get(i)));
        }

        postgreSQLDB.runQuery(postgreSQLDB.buildClearTabQuery("new_tab"));
        msServerDB.runQuery(msServerDB.buildClearTabQuery("new_tab"));
        fireBirdDB.runQuery(fireBirdDB.buildClearTabQuery("NEW_TAB"));




        expected.remove(1);

        dataSet1 = postgreSQLDB.viewTable(new String[] {"view","new_tab"});
        dataSet2 = msServerDB.viewTable(new String[] {"view","new_tab"});
        dataSet3 = fireBirdDB.viewTable(new String[] {"view","NEW_TAB"});

        assertTrue(expected.size() == dataSet1.size());
        assertTrue(expected.size() == dataSet2.size());
        assertTrue(expected.size() == dataSet3.size());

        for(int i = 0; i < expected.size(); i++){
            assertThat(dataSet1.get(i), is(expected.get(i)));
            assertThat(dataSet2.get(i), is(expected.get(i)));
            assertThat(dataSet3.get(i), is(expectedFB.get(i)));
        }


    }


    @After
    public void tear() {
        postgreSQLDB.dropTable("new_tab");
        msServerDB.dropTable("new_tab");

        fireBirdDB.closeConnection();
        fireBirdDB = new Connect(view, cmd.get("Command connect to the database.")).getDb(fireBird);
        fireBirdDB.dropTable("NEW_TAB");

        postgreSQLDB.closeConnection();
        msServerDB.closeConnection();
        fireBirdDB.closeConnection();
    }

}
