package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.Controller;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 01.11.2016.
 */

public class connectTest {
    private Controller ctrl;
    private Console console;
    private String msServer = "-ms -DBServer -Test -sa -SQL_master";
    private String postgreSQL = "-pg -localhost -test -postgres -function root";
    private String fireBird = "-fb -DBServer -D:/Andromeda/TestDB/DBase.FDB -SYSDBA -masterkey";


    @Before
    public void setup() {
        View view = new Console();
        ctrl = new Controller(view);
        console = new Console();

    }


    @Test
    public void testConnectMSServer(){

        boolean isConnect = ctrl.connect(console, msServer);
        assertTrue(isConnect);
    }

    @Test
    public void testConnectFireBird(){

        boolean isConnect = ctrl.connect(console, fireBird);
        assertTrue(isConnect);
    }

    @Test
    public void testConnectPostgreSQL(){

        boolean isConnect = ctrl.connect(console, postgreSQL);
        assertTrue(isConnect);
    }
}
