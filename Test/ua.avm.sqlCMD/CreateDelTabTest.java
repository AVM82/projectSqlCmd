package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.*;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 14.12.2016.
 */
public class CreateDelTabTest {

    private View view;
    private DataBase db;
    private final String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private final String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private final String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");
    private final String[][] columns = {
                                    {"new_Field1","integer","y","n"},
                                    {"new_Field2","varchar(20)","n","n"},
                                    {"new_Field3","varchar(10)","n","y"}
                                  };
    private ArrayList<String[]> tableColumns = new ArrayList<>();
    HashMap<String,String> cmd;
    @Before
    public void setup() {
        view = new Console();
        tableColumns.add(columns[0]);
        tableColumns.add(columns[1]);
        tableColumns.add(columns[2]);
        cmd = new Commands().getCMD();
    }

    @Test
    public void testCreateDropTabFireBird(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(fireBird);
        assertFalse(db.isTableExist("NEW_TAB"));
        db.createTab("NEW_TAB", tableColumns);
        assertTrue(db.isTableExist("NEW_TAB"));
        db.dropTable("NEW_TAB");
        assertFalse(db.isTableExist("NEW_TAB"));


    }

    @Test
    public void testCreateDelTabPestgre(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(postgreSQL);
        assertFalse(db.isTableExist("new_tab"));
        db.createTab("new_tab", tableColumns);
        assertTrue(db.isTableExist("new_tab"));
        db.dropTable("new_tab");
        assertFalse(db.isTableExist("new_tab"));

    }
    @Test
    public void testCreateTabMSServer(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(msServer);
        assertFalse(db.isTableExist("new_tab"));
        db.createTab("new_tab", tableColumns);
        assertTrue(db.isTableExist("new_tab"));
        db.dropTable("new_tab");
        assertFalse(db.isTableExist("new_tab"));

    }
}
