package ua.avm.sqlCMD.testFireBird;

import ua.avm.sqlCMD.Commands;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;
import java.util.HashMap;



public abstract class TempFB {
    protected final String[] fireBird = "connect -fb -DBServer -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey"
            .split("\u0020"+"-");

    protected String insertRow = "NEW_FIELD1=33|NEW_FIELD2=value2|NEW_FIELD3=value3";
    String updateRow = "NEW_FIELD2=newValue|NEW_FIELD2=value2";
    String deleteRow = "NEW_FIELD2=newValue";

    protected String tableName = "NEW_TAB";
    String newDbName = "D:/Andromeda/TestDB/newDB.FDB";

    private final String[][] columnsForNewTable = {
            {"NEW_FIELD1", "integer", "y", "n"},
            {"NEW_FIELD2", "varchar(20)", "n", "n"},
            {"NEW_FIELD3", "varchar(10)", "n", "y"}
    };

    protected final ArrayList<String[]> tableColumns = new ArrayList<String[]>(){{
        add(columnsForNewTable[0]);
        add(columnsForNewTable[1]);
        add(columnsForNewTable[2]);
    }};
    protected HashMap<String,String> cmd;
    protected View view = new Console();
    protected DataBase db;

    public TempFB() {

        cmd = new Commands().getCMD();

    }
}
