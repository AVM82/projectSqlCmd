package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

public class DropDB implements Command{

    private final String COMMAND_SAMPLE;
    private View view;
    private DataBase db;

    public DropDB(DataBase db, View view, String COMMAND_SAMPLE) {
        this.view = view;
        this.db = db;
        this.COMMAND_SAMPLE = COMMAND_SAMPLE;
    }

    @Override
    public boolean canDoIt(String command) {

        return COMMAND_SAMPLE.substring(0,COMMAND_SAMPLE.indexOf(view.getCommandDelimiter())).equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){
            if (view.requestForConfirmation()){

                if(db.dropDB(command[1])){

                    view.writeln("DataBase with name \""+command[1]+"\" is deleted successfully");
                }
            }
        }
    }
}
