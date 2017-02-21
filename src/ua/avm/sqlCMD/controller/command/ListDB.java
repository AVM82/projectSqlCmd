package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;


/**
 * Created by AVM on 18.11.2016.
 */
public class ListDB implements Command {
    private final String COMMAND_SAMPLE;
    private static final int COLUMN_SIZE = 30;
    private View view;
    private DataBase db;

    public ListDB(DataBase db, View view, String COMMAND_SAMPLE) {

        this.view = view;
        this.COMMAND_SAMPLE = COMMAND_SAMPLE;
        this.db = db;

    }

    @Override
    public boolean canDoIt(String command) {
        return COMMAND_SAMPLE.equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){

            view.printTableData(db.getListDB(), new String[]{"DATABASE NAME","OWNER"},COLUMN_SIZE);
        }
    }
}
