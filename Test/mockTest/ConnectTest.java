package mockTest;

import integration.expected;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.avm.sqlCMD.controller.Commands;
import ua.avm.sqlCMD.controller.ExitException;
import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;

public class ConnectTest {
    private View view;
    private DataBase database;


    @Before
    public void setup() {

        view = Mockito.mock(View.class);
        database = Mockito.mock(DataBase.class);
        Mockito.when(view.getCommandDelimiter()).thenReturn("\u0020" + "-");

    }

    @Test
    public void testCanDoItConnect() {
        // given
        Command command = new Connect(view, Commands.getCMD().get("Command connect to the database."));

        // when
        boolean canProcess = command.canDoIt("connect");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanDoItConnectQwe() {
        // given
        Command command = new Connect(view, Commands.getCMD().get("Command connect to the database."));

        // when
        boolean canProcess = command.canDoIt("Qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testDoItConnectCommandWithExtraArg() {
        // given
        Command command = new Connect(view, Commands.getCMD().get("Command connect to the database."));

        // when
        command.doIt(new String[]{"connect", "pg", "localhost", "test", "postgres", "postgres", "1"});

        // then
        Mockito.verify(view).warningWriteln("Invalid number of parameters. Expected 5 or 4, but got 6");
    }

    @Test
    public void testDoItConnectCommandWithFewArg() {
        // given
        Command command = new Connect(view, Commands.getCMD().get("Command connect to the database."));

        // when

        command.doIt(new String[]{"connect", "pg", "localhost", "test"});

        // then
        Mockito.verify(view).warningWriteln("Invalid number of parameters. Expected 5 or 4, but got 3");

    }

}
