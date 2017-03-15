package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.view.View;

import java.util.HashMap;

public class UnknownCommand implements Command {
    private View view;
    private HashMap<String,String> commands = new HashMap<>();
    private String commandHelp;


    public UnknownCommand(View view, HashMap<String,String> commands, String commandHelp) {
        this.view = view;
        this.commands = commands;
        this.commandHelp = commandHelp;

    }

    @Override
    public boolean canDoIt(String command) {
        return true;
    }

    @Override
    public void doIt(String[] command) {

        view.warningWriteln("Unknown command!");
        view.writeln("");
        new Help(view, commands, commandHelp).doIt(new String[] {commandHelp});
    }
}
