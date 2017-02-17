package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 09.02.2017.
 */
public class getDataByQueryTest {


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
    public void testForPostgreSQL(){
        db = new Connect(view).getDb(postgreSQL);
        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"name","password"});
        expected.add(new String[]{"AVM","password"});

        ArrayList<String[]> dataSet = db.getDataByQuery("select * from public.user where name = 'AVM'");
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }



    }
    @Test
    public void testForMSSQL(){
        db = new Connect(view).getDb(msServer);
        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"id","name","password"});
        expected.add(new String[]{"1","avm","password"});

        ArrayList<String[]> dataSet = db.getDataByQuery("use avm select * from LP where name = 'avm'");
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }



    }
    @Test
    public void testForFireBird(){
        db = new Connect(view).getDb(fireBird);
        ArrayList<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"A_SYS","A_SYS_VALUE"});
        expected.add(new String[]{"Lic","AVM"});

        ArrayList<String[]> dataSet = db.getDataByQuery("select * from A_SYS where A_SYS = 'Lic'");
        assertTrue(expected.size() == dataSet.size());
        for(int i = 0; i < dataSet.size(); i++){
            assertThat(dataSet.get(i), is(expected.get(i)));
        }



    }
}
