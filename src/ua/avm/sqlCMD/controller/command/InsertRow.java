package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

public class InsertRow  implements Command{
    private final String COMMAND_SAMPLE;
    private View view;
    private DataBase db;

    public InsertRow(DataBase db, View view, String COMMAND_SAMPLE) {

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
            view.write("Table ");
            view.write(command[1]);
            view.writeln(" has following columns");
            view.write("|");
            for (String aColumnList : columnList) {
                view.write(aColumnList);
                view.write("|");
            }
            view.writeln("");
            view.writeln("Enter a new row of table:");
            view.writeln("row1=value1|row2=value2|...|rowN=valueN");
            view.writeln("***********************************************************************************************");
            view.writeln("");
            db.runQuery(db.buildInsertQuery(view.read().split("\\|"),command[1]));


        }
    }
}
