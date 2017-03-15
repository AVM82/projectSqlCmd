package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;

public class RunQuery implements Command {

    private static final int COLUMN_SIZE = 20;
    private final String COMMAND_SAMPLE;
    private View view;
    private DataBase db;

    public RunQuery(DataBase db, View view, String COMMAND_SAMPLE) {

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
