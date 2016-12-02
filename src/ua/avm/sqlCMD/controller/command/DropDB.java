package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 02.12.2016.
 */
public class DropDB implements Command{

    private final View view;
    private final DataBase db;

    public DropDB(DataBase db, View view) {
        this.view = view;
        this.db = db;
    }

    @Override
    public boolean canDoIt(String command) {
        return "dropdb".equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if(db.dropDB(command[1])){

            view.writeln("DataBase with name \""+command[1]+"\" is deleted successfully");
        }



    }
}
