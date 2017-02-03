package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;

/**
 * Created by AVM on 02.02.2017.
 */
public class RunQuery implements Command {

    private static final int COLUMN_SIZE = 20;
    private final String COMMAND_SAMPLE = "query -queryLine";
    private View view;
    private DataBase db;

    public RunQuery(DataBase db, View view) {

        this.view = view;
        this.db = db;

    }

    @Override
    public boolean canDoIt(String command) {
        return COMMAND_SAMPLE.substring(0,COMMAND_SAMPLE.indexOf(view.getCommandDelimiter())).equals(command);
    }

    @Override
    public void doIt(String[] command) {
        String queryLine = command[1].trim();
        if(queryLine.substring(0,queryLine.indexOf("\u0020")).equals("select")){
            ArrayList<String[]> dataSet = db.getDataByQuery(command[1]);
            String[] columnList = dataSet.get(0);
            view.printTitle(columnList, COLUMN_SIZE);
            view.printTableData(dataSet, COLUMN_SIZE);
            view.printFooter(columnList.length * COLUMN_SIZE);

        }else {
            db.runQuery(command[1]);
        }


    }
}
