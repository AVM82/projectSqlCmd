package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by AVM on 18.11.2016.
 */
public class ListDB implements Command {
    private final View view;
    private DataBase db;

    public ListDB(DataBase db, View view) {

        this.view = view;
        this.db = db;

    }

    @Override
    public boolean canDoIt(String command) {
        return "ldb".equals(command);
    }

    @Override
    public boolean doIt(String[] command) {

        Map <String, String> tableList = db.getListDB();

        Set<Map.Entry<String, String>> set = tableList.entrySet();
        for (Map.Entry<String, String> me : set) {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }

        return false;
    }
}
