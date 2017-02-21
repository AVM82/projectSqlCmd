package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;



/**
 * Created by AVM on 05.12.2016.
 */
public class ListTab implements Command {

    private View view;
    private DataBase db;
    private final String COMMAND_SAMPLE;
    private static final int COLUMN_SIZE = 30;

    public ListTab(DataBase db, View view, String COMMAND_SAMPLE) {
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

            view.printTableData(db.getListTable(), new String[]{"TABLE NAME","ROW COUNT"},COLUMN_SIZE);


        }


    }
}
