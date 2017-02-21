package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.view.View;

import java.util.HashMap;

/**
 * Created by AVM on 17.02.2017.
 */
public class Help implements Command {

    private View view;
    private final String COMMAND_SAMPLE;
    private HashMap<String,String> commands = new HashMap<>();

    public Help(View view, HashMap<String,String> commands, String COMMAND_SAMPLE) {

        this.view = view;
        this.commands = commands;
        this.COMMAND_SAMPLE = COMMAND_SAMPLE;

    }


    @Override
    public boolean canDoIt(String command) {
        return COMMAND_SAMPLE.equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){
            view.write("Existing commands:\n");
            for (String key : commands.keySet()) {
                view.writeln("\t"+commands.get(key));
                view.writeln("\t\t-"+key+"\n");
            }
        }
    }
}
