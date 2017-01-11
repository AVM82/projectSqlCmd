package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 02.11.2016.
 */
public class Exit implements Command {

    private final View view;
    private String COMMAND_SAMPLE = "exit";

    public Exit(View view) {

        this.view = view;
    }

    @Override
    public boolean canDoIt(String command) {
        return "exit".equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (!Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){
            return;
        }
        view.write("The work is completed");
        System.exit(0);

    }
}
