package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by AVM on 30.11.2016.
 */
public class listDBTest {

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
    public void testGetListDBPostgreSQL(){

        db = new Connect(view).getDb(postgreSQL);

        HashMap<String, String> listDB = db.getListDB();
        HashMap<String, String> expected  = new HashMap<>();

        expected.put("test","postgres");
        expected.put("postgres","postgres");

        assertThat(listDB, is(expected));
        db.closeConnection();

    }
    @Test
    public void testGetListDBMSServer(){

        db = new Connect(view).getDb(msServer);
        HashMap<String, String> listDB = db.getListDB();
        HashMap<String, String> expected  = new HashMap<>();

        expected.put("DRUG","sa");
        expected.put("TEST","sa");
        expected.put("ACINUS","sa");

        assertThat(listDB, is(expected));
        db.closeConnection();


    }
    @Test
    public void testGetListDBFireBird(){

        db = new Connect(view).getDb(fireBird);
        HashMap<String, String> listDB = db.getListDB();

        assertNull(listDB);

        db.closeConnection();


    }

}
