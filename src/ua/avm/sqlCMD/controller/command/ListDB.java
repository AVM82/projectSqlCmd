package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Created by AVM on 18.11.2016.
 */
public class ListDB implements Command {
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

        int sizeCol = 20;//column size

        Map <String, String> tableList = db.getListDB();
        if (tableList == null) {
            view.warningWriteln("Command not supported for this database.");
        }

        Set<Map.Entry<String, String>> set = tableList.entrySet();

        for (int i = 0; i < sizeCol*2+3; i++) {
            view.write("_");
        }
        view.writeln("");
        view.write("|");
        view.fWriteln("DB NAME",sizeCol);
        view.write("|");
        view.fWriteln("OWNER",sizeCol);
        view.writeln("|");
        for (int i = 0; i < sizeCol*2+3; i++) {
            view.write("=");
        }
        view.writeln("");
        for (Map.Entry<String, String> value : set) {

            String string1 = value.getKey();
            String string2 = value.getValue();
            view.write("|");
            view.fWriteln(string1,sizeCol);
            view.write("|");
            view.fWriteln(string2,sizeCol);
            view.write("|");
            System.out.println("");
        }
        for (int i = 0; i < sizeCol*2+3; i++) {
            view.write("-");
        }
        System.out.println("");

    }
}
