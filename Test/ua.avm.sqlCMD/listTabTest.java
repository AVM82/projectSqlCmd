package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by AVM on 30.11.2016.
 */
public class listTabTest {

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
    public void testGetListTabPostgreSQL(){

        db = new Connect(view).getDb(postgreSQL);

        Map<String, String> listTab = db.getListTable();
        Map<String, String> expected  = new HashMap<>();

        expected.put("user","0");

        assertThat(listTab, is(expected));
        db.closeConnection();

    }
    @Test
    public void testGetListTabMSServer(){

        db = new Connect(view).getDb(msServer);
        Map<String, String> listTab = db.getListTable();
        HashMap<String, String> expected  = new HashMap<>();

        expected.put("dbo.user","1");

        assertThat(listTab, is(expected));
        db.closeConnection();


    }
    @Test
    public void testGetListTabFireBird(){

        db = new Connect(view).getDb(fireBird);
        Map<String, String> listTab = db.getListTable();
        Map<String, String> expected  = new HashMap<>();

        expected.put("LINE_VISIT","3585");
        expected.put("PATIENT_VISIT","2718");
        expected.put("R_STATUS","2");
        expected.put("LINE_VISIT_TEMPLATE","85");
        expected.put("PATIENT_LV","23915");
        expected.put("SCR_FAILURE","9");
        expected.put("CARD","158");
        expected.put("RESEARCH","19");
        expected.put("VISITS","447");
        expected.put("VISITE_TYPE","3");
        expected.put("A_ACCESS","3");
        expected.put("A_SYS","3");

        assertThat(listTab, is(expected));
        db.closeConnection();



    }

}
