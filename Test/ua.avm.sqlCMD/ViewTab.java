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
import static org.junit.Assert.*;

/**
 * Created by AVM on 30.01.2017.
 */
public class ViewTab {

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
    public void viewTablePostgreSQL(){


        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"name","password"});
        expected.add(new String[]{"AVM","password"});
        expected.add(new String[]{"admin","*****"});
        expected.add(new String[]{"user1","qwerty"});

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(postgreSQL);
        ArrayList<String[]> dataSet = db.viewTable(new String[] {"view","user"});
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }

    }
    @Test
    public void viewTableMSServer(){

        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"id","name","password"});
        expected.add(new String[]{"1","avm","password"});

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(msServer);
        ArrayList<String[]> dataSet = db.viewTable(new String[] {"view","lp"});
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }

    }
    @Test
    public void viewTableFireBird(){

        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"A_SYS","A_SYS_VALUE"});
        expected.add(new String[]{"Lic","AVM"});

        db = new Connect(view,cmd.get("Command connect to the database.")).getDb(fireBird);
        ArrayList<String[]> dataSet = db.viewTable(new String[] {"view","A_SYS","1","2"});
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }

    }
}
