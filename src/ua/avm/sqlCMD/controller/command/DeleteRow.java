package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 02.02.2017.
 */
public class DeleteRow implements Command {

    private final String COMMAND_SAMPLE;
    private View view;
    private DataBase db;

    public DeleteRow(DataBase db, View view,String COMMAND_SAMPLE) {

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

        if (Utility.verifyParams(COMMAND_SAMPLE, view.getCommandDelimiter(), command.length - 1, view)) {
            if (!db.isTableExist(command[1])) {
                view.warningWriteln("Table is not exist.");
                view.writeln("");
                return;
            }
            String[] columnList = db.getColumnList(command[1]);
            view.writeln("***********************************************************************************************");
            view.writeln("Table " + command[1] + " has following columns");
            view.write("|");
            for (int i = 0; i < columnList.length; i++) {
                view.write(columnList[i] + "|");
            }
            view.writeln("");
            view.writeln("Enter a condition for delete row of table:\n"
                    + "columnName=value\n"
                    + "***********************************************************************************************\n");
            view.writeln("");
            db.runQuery(db.buildDeleteQuery(view.read().split("\\="),command[1]));
        }

    }
}
