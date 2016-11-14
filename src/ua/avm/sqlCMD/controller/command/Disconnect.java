package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.view.View;


/**
 * Created by AVM on 04.11.2016.
 */
public class Disconnect implements Command {

    private final View view;



    public Disconnect(View view) {

        this.view = view;

    }

    @Override
    public boolean canDoIt(String command) {
        return "disconnect".equals(command);
    }

    @Override
    public boolean doIt(String[] command) {

        view.writeln("Connection with database is closed.");
        return true;
    }
}
