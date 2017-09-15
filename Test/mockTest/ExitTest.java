package mockTest;


import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.avm.sqlCMD.controller.Commands;
import ua.avm.sqlCMD.controller.ExitException;
import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.Exit;
import ua.avm.sqlCMD.view.View;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

public class ExitTest {

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
    public void testCanDoItExitWithParam() {
        // given
        Command command = new Exit(view, Commands.getCMD().get("Exit the program."));
        Mockito.when(view.getCommandDelimiter()).thenReturn("\u0020" + "-");

        // when
        command.doIt(new String[]{"exit", "param"});

        // then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, atLeastOnce()).warningWriteln(argumentCaptor.capture());
        assertEquals("[Invalid number of parameters. Expected 0, but got 1, The \"Exit\" command has no parameters]",argumentCaptor.getAllValues().toString());

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
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view).write(argumentCaptor.capture());
        assertEquals("The work is completed",argumentCaptor.getValue().toString());

    }




}
