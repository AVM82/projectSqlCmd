package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by AVM on 30.01.2017.
 */
public class viewTab {

    private View view;
    private DataBase db;
    private final String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private final String[] msServer = "connect -ms -DBServer -avm -sa -SQL_master".split("\u0020"+"-");
    private final String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey".split("\u0020"+"-");
    @Before
    public void setup() {

        view = new Console();

    }

    @Test
    public void viewTablePostgreSQL(){


        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"name","password"});
        expected.add(new String[]{"AVM","password"});
        expected.add(new String[]{"admin","*****"});
        expected.add(new String[]{"user1","qwerty"});

        db = new Connect(view).getDb(postgreSQL);
        ArrayList<String[]> table = db.viewTable(new String[] {"view","user"});
        for(int i = 0; i < table.size(); i++){
            assertThat(table.get(i), is(expected.get(i)));
        }

    }
    @Test
    public void viewTableMSServer(){

        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"id","name","password"});
        expected.add(new String[]{"1","avm","password"});

        db = new Connect(view).getDb(msServer);
        ArrayList<String[]> table = db.viewTable(new String[] {"view","lp"});
        for(int i = 0; i < table.size(); i++){
            assertThat(table.get(i), is(expected.get(i)));
        }

    }
    @Test
    public void viewTableFireBird(){

        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"A_SYS","A_SYS_VALUE"});
        expected.add(new String[]{"Lic","AVM"});

        db = new Connect(view).getDb(fireBird);
        ArrayList<String[]> table = db.viewTable(new String[] {"view","A_SYS","1","2"});
        for(int i = 0; i < table.size(); i++){
            assertThat(table.get(i), is(expected.get(i)));
        }

    }
}
