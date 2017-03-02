package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.view.View;

import java.util.HashMap;

/**
 * Created by AVM on 02.03.2017.
 */
public class UnknownCommand implements Command {
    private View view;
    private HashMap<String,String> commands = new HashMap<>();
//    private final String COMMAND_SAMPLE;


    public UnknownCommand(View view, HashMap<String,String> commands) {
        this.view = view;
        this.commands = commands;
    }

    @Override
    public boolean canDoIt(String command) {
        return true;
    }

    @Override
    public void doIt(String[] command) {

        view.warningWriteln("Unknown command!");
        view.writeln("");
        String commandHelp = commands.get("Command to view help.");
        new Help(view, commands, commandHelp).doIt(new String[] {commandHelp});
    }
}
