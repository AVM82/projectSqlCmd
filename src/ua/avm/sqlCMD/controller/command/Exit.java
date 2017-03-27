package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.ExitException;
import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.view.View;

public class Exit implements Command {

    private View view;
    private final String COMMAND_SAMPLE;

    public Exit(View view, String COMMAND_SAMPLE) {

        this.view = view;
        this.COMMAND_SAMPLE = COMMAND_SAMPLE;
    }

    @Override
    public boolean canDoIt(String command) {
        return COMMAND_SAMPLE.equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){
            view.write("The work is completed");
            throw new ExitException();
        }
    }
}
