package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;

/**
 * Created by AVM on 05.01.2017.
 */
public class CreateTab implements Command{

    private final String COMMAND_SAMPLE = "createTab -tableName";
    private final String COLUMN_SAMPLE = "new_Field1|varchar|y|n";
    private View view;
    private DataBase db;

    public CreateTab(DataBase db, View view) {

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


            if (Utility.verifyName(command[1])) {
                view.warningWriteln("Wrong table name.");
                view.writeln("");
                return;
            }
            if (db.isTableExist(command[1])) {
                view.warningWriteln("Table is already exist.");
                view.writeln("");
                return;
            }
            ArrayList<String[]> tableColumns = new ArrayList<>();
            view.writeln("***********************************************************************************************");
            view.writeln("Enter a new column of table: field name|field type|if the field is primary(y/n)|if is NULL (y/n)\n"
                    + "end\n"
                    + "For example:\n"
                    + "new_Field1|integer|y|n\n"
                    + "new_Field2|varchar(20)|n|n\n"
                    + "new_Field3|varchar(10)|n|y\n"
                    + "end\n"
                    + "***********************************************************************************************\n");
            view.writeln("");
            String line = view.read();
            while (!line.equals("end")) {
                String[] column = line.split("\\|");
                boolean canRec = Utility.verifyParams(COLUMN_SAMPLE, view.getSecondaryDelimiter(), column.length-1, view);
                if ((canRec)&&(!isDoubleField(tableColumns,column[0]))) {

                    tableColumns.add(column);

                }
                line = view.read();
            }
            if (db.createTab(command[1], tableColumns)) {
                view.writeln("Table with name \"" + command[1] + "\" is created successfully.");
            } else {
                view.warningWriteln("ERROR! Something went wrong :(");
            }

        }
    }
    private boolean isDoubleField(ArrayList<String[]> allColumns, String thisColumn){

        for (String[] s : allColumns) {
            if (s[0].equals(thisColumn)) {
               view.warningWriteln(String.format("Field name %s already typed", thisColumn));
               view.writeln("");
               return true;
            }
        }
        return false;

    }
}
