package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.CreateDB;
import ua.avm.sqlCMD.controller.command.DropDB;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 01.12.2016.
 */
public class createDropDB {

    View view;
    DataBase db;
    private String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private String[] msServer = "connect -ms -DBServer -Test -sa -SQL_master".split("\u0020"+"-");
    private String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/DBase.FDB -SYSDBA -masterkey".split("\u0020"+"-");
    @Before
    public void setup() {

        view = new Console();

    }

    @Test
    public void testCreateDropDBFireBird(){

        db = new Connect(view).getDb(fireBird);
        new CreateDB(db, view).doIt(new String[] {"","D:/Andromeda/TestDB/newDB.FDB"});
        assertTrue(db.isDataBaseExist("D:/Andromeda/TestDB/newDB.FDB"));
        new DropDB(db,view).doIt(new String[] {"","D:/Andromeda/TestDB/newDB.FDB"});
        assertFalse(db.isDataBaseExist("D:/Andromeda/TestDB/newDB.FDB"));


    }

    @Test
    public void testCreateDropDBPestgre(){

        db = new Connect(view).getDb(postgreSQL);
        new CreateDB(db, view).doIt(new String[] {"","newdb"});
        assertTrue(db.isDataBaseExist("newdb"));
        new DropDB(db,view).doIt(new String[] {"","newdb"});
        assertFalse(db.isDataBaseExist("newdb"));
    }
    @Test
    public void testCreateDBMSServer(){

        db = new Connect(view).getDb(msServer);
        new CreateDB(db, view).doIt(new String[] {"","newdb"});
        assertTrue(db.isDataBaseExist("newdb"));
        new DropDB(db,view).doIt(new String[] {"","newdb"});
        assertFalse(db.isDataBaseExist("newdb"));



    }

}
