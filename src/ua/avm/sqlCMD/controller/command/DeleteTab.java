package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 10.01.2017.
 */
public class DeleteTab implements Command{

    private String COMMAND_SAMPLE = "delTab -tableName";
    private final View view;
    private final DataBase db;

    public DeleteTab(DataBase db, View view) {

        this.view = view;
        this.db = db;

    }

    @Override
    public boolean canDoIt(String command) {
        return "deltab".equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (!Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){
            return;
        }

        if (Utility.requestForConfirmation(view,command[1])){

            if(db.dropTable(command[1])){

                view.writeln("Table with name \""+command[1]+"\" is deleted successfully");
            }
        }

    }
}
