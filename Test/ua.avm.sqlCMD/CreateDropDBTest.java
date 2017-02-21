package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.CreateDB;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 01.12.2016.
 */
public class CreateDropDBTest {

    private View view;
    private DataBase db;
    private final String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private final String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private final String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");
    HashMap<String,String> cmd;
    @Before
    public void setup() {

        view = new Console();
        cmd = new Commands().getCMD();

    }

    @Test
    public void testCreateDropDBFireBird(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(fireBird);
        new CreateDB(db,view,cmd.get("Command creates a new database.")).doIt(new String[] {"","D:/Andromeda/TestDB/newDB.FDB"});
        assertTrue(db.isDataBaseExist("D:/Andromeda/TestDB/newDB.FDB"));
//        new DropDB(db,view).doIt(new String[] {"","D:/Andromeda/TestDB/newDB.FDB"});
        db.dropDB("D:/Andromeda/TestDB/newDB.FDB");
        assertFalse(db.isDataBaseExist("D:/Andromeda/TestDB/newDB.FDB"));


    }

    @Test
    public void testCreateDropDBPestgre(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(postgreSQL);
        new CreateDB(db, view,cmd.get("Command creates a new database.")).doIt(new String[] {"","newdb"});
        assertTrue(db.isDataBaseExist("newdb"));
//        new DropDB(db,view).doIt(new String[] {"","newdb"});
        db.dropDB("newdb");
        assertFalse(db.isDataBaseExist("newdb"));
    }
    @Test
    public void testCreateDBMSServer(){

        db = new Connect(view, cmd.get("Command connect to the database.")).getDb(msServer);
        new CreateDB(db, view,cmd.get("Command creates a new database.")).doIt(new String[] {"","newdb"});
        assertTrue(db.isDataBaseExist("newdb"));
//        new DropDB(db,view).doIt(new String[] {"","newdb"});
        db.dropDB("newdb");
        assertFalse(db.isDataBaseExist("newdb"));



    }

}
