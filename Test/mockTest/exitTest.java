package mockTest;


import org.junit.Test;
import org.mockito.Mockito;
import ua.avm.sqlCMD.controller.Commands;
import ua.avm.sqlCMD.controller.ExitException;
import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.Exit;
import ua.avm.sqlCMD.view.View;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class exitTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanDoItExit() {
        // given
        Command command = new Exit(view, Commands.getCMD().get("Exit the program."));

        // when
        boolean canProcess = command.canDoIt("exit");

        // then
        assertTrue(canProcess);
    }
    @Test
    public void testCanDoItExitQwe() {
        // given
        Command command = new Exit(view, Commands.getCMD().get("Exit the program."));

        // when
        boolean canProcess = command.canDoIt("qwe");

        // then
        assertFalse(canProcess);
    }
    @Test
    public void testDoItExitCommand_throwsExitException() {
        // given
        Command command = new Exit(view, Commands.getCMD().get("Exit the program."));
        Mockito.when(view.getCommandDelimiter()).thenReturn("\u0020"+"-");

        // when
        try {
            command.doIt(new String[]{"exit"});
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }

        // then
        Mockito.verify(view).write("The work is completed");
    }




}
