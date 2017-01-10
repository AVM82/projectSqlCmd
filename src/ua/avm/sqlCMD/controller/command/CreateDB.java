package ua.avm.sqlCMD.controller.command;


import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;


/**
 * Created by AVM on 30.11.2016.
 */
public class CreateDB implements Command {
    private final View view;
    private final DataBase db;

    public CreateDB(DataBase db, View view) {
        this.view = view;
        this.db = db;
    }

    @Override
    public boolean canDoIt(String command) {
        return "createDB".equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if(db.createDB(command[1])){

            view.writeln("DataBase with name \""+command[1]+"\" is created successfully");
        }



    }
}
