package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;


public class Disconnect implements Command {

    private View view;
    private DataBase db;
    private final String COMMAND_SAMPLE;



    public Disconnect(DataBase db, View view, String COMMAND_SAMPLE) {

        this.view = view;
        this.db = db;
        this.COMMAND_SAMPLE = COMMAND_SAMPLE;

    }

    @Override
    public boolean canDoIt(String command) {
        return COMMAND_SAMPLE.equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){
            if (db == null){
                view.writeln("There are no active connections!");
            }
            else {
                db.closeConnection();
                view.setPrefix(">");
                view.writeln("Connection with database is closed.");
            }
        }
    }
}
