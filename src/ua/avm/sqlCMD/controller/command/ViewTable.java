package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 16.01.2017.
 */
public class ViewTable implements Command{

    private View view;
    private DataBase db;
    private final String COMMAND_SAMPLE = "view -tableName -limit -offset";
    private final int COLUMN_SIZE = 20;

    public ViewTable(DataBase db, View view) {
        this.view = view;
        this.db = db;
    }


    @Override
    public boolean canDoIt(String command) {

        return COMMAND_SAMPLE.substring(0,COMMAND_SAMPLE.indexOf(view.getCommandDelimiter())).equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (Utility.verifyParams(new int[]{1,2,3},command.length - 1,view)){

            view.printTitle(db.getColumnList(command[1]), COLUMN_SIZE);

            view.printTableData(db.viewTable(command), COLUMN_SIZE);

            view.printFooter(COLUMN_SIZE);
        }

    }
}
