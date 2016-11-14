package ua.avm.sqlCMD;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.controller.Controller;
import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.model.DBFireBird;
import ua.avm.sqlCMD.model.MSServer;
import ua.avm.sqlCMD.model.PostgreSQL;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import static org.junit.Assert.assertTrue;

/**
 * Created by AVM on 01.11.2016.
 */

public class connectTest {
    private Controller ctrl;
    private Command cmd;
    private String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");
    private String[] msServer = "connect -ms -DBServer -Test -sa -SQL_master".split("\u0020"+"-");
    private String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/DBase.FDB -SYSDBA -masterkey".split("\u0020"+"-");

    public connectTest() throws Exception {
    }


    @Before
    public void setup() throws Exception {
        View view = new Console();
        ctrl = new Controller(view);
        cmd = new Connect(new PostgreSQL(postgreSQL),view);
    }

    @Test
    public void testConnectMSServer(){

        boolean isConnect = cmd.doIt(msServer);
        assertTrue(isConnect);
    }

    @Test
    public void testConnectFireBird(){

        boolean isConnect = cmd.doIt(fireBird);
        assertTrue(isConnect);
    }

    @Test
    public void testConnectPostgreSQL(){

        boolean isConnect = cmd.doIt(postgreSQL);
        assertTrue(isConnect);
    }
}
