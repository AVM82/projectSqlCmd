package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;


/**
 * Created by AVM on 04.11.2016.
 */
public class Disconnect implements Command {

    private final View view;
    private DataBase db;
    private String COMMAND_SAMPLE = "disconnect";



    public Disconnect(DataBase db, View view) {

        this.view = view;
        this.db = db;

    }

    @Override
    public boolean canDoIt(String command) {
        return "disconnect".equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (!Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){
            return;
        }

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
