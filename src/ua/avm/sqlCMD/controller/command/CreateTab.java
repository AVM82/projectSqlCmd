package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;

/**
 * Created by AVM on 05.01.2017.
 */
public class CreateTab implements Command{

    private String COMMAND_SAMPLE = "createTab -tableName";
    private String COLUMN_SAMPLE = "new_Field1|varchar|y|n";
    private final View view;
    private final DataBase db;

    public CreateTab(DataBase db, View view) {

        this.view = view;
        this.db = db;

    }


    @Override
    public boolean canDoIt(String command) {
        return "createTab".equals(command);
    }

    @Override
    public void doIt(String[] command) {
        int countOfParam = Utility.countOfParam(COMMAND_SAMPLE, view.getCommandDelimiter()) - 1;
        int commandLength = command.length - 1;
        if (commandLength != countOfParam)
        {
            view.warningWriteln(String.format("Invalid number of parameters. Expected %s, but got %s",
                    countOfParam, commandLength));
            return;
        }

        if (Utility.verifyName(command[1])){
            view.warningWriteln("Wrong table name.");
            view.writeln("");
            return;
        }
        if (db.isTableExist(command[1])){
            view.warningWriteln("Table is already exist.");
            view.writeln("");
            return;
        }
        ArrayList<String[]> tableColumns = new ArrayList<>();
        view.writeln("***********************************************************************************************");
        view.writeln("Enter a new column of table: field name|field type|if the field is primary(y/n)|if is NULL (y/n)");
        view.writeln("end");
        view.writeln("For example:");
        view.writeln("new_Field1|integer|y|n");
        view.writeln("new_Field2|varchar(20)|n|n");
        view.writeln("new_Field3|varchar(10)|n|y");
        view.writeln("end");
        view.writeln("***********************************************************************************************");
        view.writeln("");
        String line = view.read();
        while (!line.equals("end")) {
            boolean canRec = true;
            countOfParam = Utility.countOfParam(COLUMN_SAMPLE, view.getSecondaryDelimiter());
            String[] column = line.split("\\|");
            if (column.length != countOfParam){
                view.warningWriteln(String.format("Invalid number of parameters. Expected %s, but got %s",
                        countOfParam, column.length));
                canRec = false;
            }else {
                for (String[] s : tableColumns) {
                    if (s[0].equals(column[0])) {
                        view.warningWriteln(String.format("Field name %s already typed", column[0]));
                        view.writeln("");
                        canRec = false;
                        break;
                    }
                }
            }
            if (canRec){

                tableColumns.add(column);

            }
            line = view.read();
        }
        db.createTab(command[1], tableColumns);
    }
}
