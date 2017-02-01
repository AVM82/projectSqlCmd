package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 31.01.2017.
 */
public class InsertRow  implements Command{
    private final String COMMAND_SAMPLE = "insert -tableName";
//    private final String ROW_SAMPLE = "new_Field1|varchar|y|n";
    private View view;
    private DataBase db;

    public InsertRow(DataBase db, View view) {

        this.view = view;
        this.db = db;

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
            view.writeln("Table " +command[1]+" has following columns");
            view.write("|");
            for (int i = 0; i < columnList.length; i++) {
                view.write(columnList[i]+"|");
            }
            view.writeln("");
            view.writeln("Enter a new row of table:"
                    + "row1=value1|row2=value2|...|rowN=valueN\n"
                    + "***********************************************************************************************\n");
            view.writeln("");
            db.insertRow(db.buildInsertQuery(view.read().split("\\|"),command[1]));


        }
    }
}
