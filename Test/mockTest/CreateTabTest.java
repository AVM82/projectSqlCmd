package mockTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.avm.sqlCMD.controller.Commands;
import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.CreateTab;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateTabTest {
    private DataBase db;
    private View view;
    private String COMMAND_SAMPLE;


    @Before
    public void setup() {

        COMMAND_SAMPLE = Commands.getCMD().get("Command creates a new table.");
        view = Mockito.mock(View.class);
        db = Mockito.mock(DataBase.class);
        Mockito.when(view.getCommandDelimiter()).thenReturn("\u0020" + "-");

    }

    @Test
    public void testCanDoItCreate() {
        // given
        Command command = new CreateTab(db, view, Commands.getCMD().get("Command creates a new table."));

        // when
        boolean canProcess = command.canDoIt("create");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanDoItCreateQwe() {
        // given
        Command command = new CreateTab(db, view, Commands.getCMD().get("Command creates a new table."));

        // when
        boolean canProcess = command.canDoIt("Qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testDoItCreateCommandWithWrongTableName() {
        // given
        Command command = new CreateTab(db, view, Commands.getCMD().get("Command creates a new table."));

        // when
        command.doIt(new String[]{"create", "123pg"});

        // then
        Mockito.verify(view).warningWriteln("Wrong table name.");
    }
    @Test

    public void testDoItCreateCommandWithTableExist() {
        // given
        Command command = new CreateTab(db, view, Commands.getCMD().get("Command creates a new table."));
        Mockito.when(db.isTableExist("pg")).thenReturn(true);

        // when
        command.doIt(new String[]{"create", "pg"});

        // then
        Mockito.verify(view).warningWriteln("Table is already exist.");
    }
    @Test

    public void testDoItCreateCommandCreateTable() {
        // given
        Command command = new CreateTab(db, view, Commands.getCMD().get("Command creates a new table."));
        Mockito.when(view.read()).thenReturn("end");
        Mockito.when(db.createTab("",null)).thenReturn(false);

        // when
        command.doIt(new String[]{"create", "pg"});

        // then
        Mockito.verify(view).warningWriteln("ERROR! Something went wrong :(");
    }

}
