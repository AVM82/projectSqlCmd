package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.*;

/**
 * Created by AVM on 18.11.2016.
 */
public class ListDB implements Command {
    private String COMMAND_SAMPLE = "ldb";
    private final View view;
    private final DataBase db;

    public ListDB(DataBase db, View view) {

        this.view = view;
        this.db = db;

    }

    @Override
    public boolean canDoIt(String command) {
        return "ldb".equals(command);
    }

    @Override
    public void doIt(String[] command) {

        if (Utility.verifyParams(COMMAND_SAMPLE,view.getCommandDelimiter(),command.length - 1, view)){

            Utility.printTab(db.getListDB(),view, new String[]{"DATABASE NAME","OWNER"},20);
        }

    }
}
