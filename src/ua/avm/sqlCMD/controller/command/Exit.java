package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 02.11.2016.
 */
public class Exit implements Command {

    private final View view;

    public Exit(View view) {

        this.view = view;
    }

    @Override
    public boolean canDoIt(String command) {
        return "exit".equals(command);
    }

    @Override
    public boolean doIt(String[] command) {
        view.write("The work is completed");
        System.exit(0);

        return true;
    }
}