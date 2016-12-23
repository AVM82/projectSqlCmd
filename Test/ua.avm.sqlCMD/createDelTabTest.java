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
 * Created by AVM on 14.12.2016.
 */
public class createDelTabTest {

    View view;
    DataBase db;
    private String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");
    @Before
    public void setup() {

        view = new Console();

    }

    @Test
    public void testCreateDropTabFireBird(){

        db = new Connect(view).getDb(fireBird);

        assertTrue(db.isTableExist("A_SYS"));

    }

    @Test
    public void testCreateDelTabPestgre(){

        db = new Connect(view).getDb(postgreSQL);

        assertTrue(db.isTableExist("user"));

    }
    @Test
    public void testCreateTabMSServer(){

        db = new Connect(view).getDb(msServer);

        assertTrue(db.isTableExist("LP"));

    }
}
