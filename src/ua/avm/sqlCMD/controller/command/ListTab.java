package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;



/**
 * Created by AVM on 05.12.2016.
 */
public class ListTab implements Command {

    private final View view;
    private final DataBase db;
    private String COMMAND_SAMPLE = "ltab";

    public ListTab(DataBase db, View view) {
        this.view = view;
        this.db = db;
    }

    @Override
    public boolean canDoIt(String command) {
        if ("ltab".equals(command)){
            if ((db.isConnect())&&(db.getDbaseName()!= null)){
                return true;
            }else {
                view.warningWriteln("To execute this command you must be connected to the database.");
                return false;
            }

        }else {
            return false;
        }
    }

    @Override
    public void doIt(String[] command) {
        if (Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){

            Utility.printTab(db.getListTable(),view, new String[]{"TABLE NAME","ROW COUNT"},30);

        }


    }
}
