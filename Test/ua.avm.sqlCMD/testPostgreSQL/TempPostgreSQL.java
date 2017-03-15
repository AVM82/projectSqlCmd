package ua.avm.sqlCMD.testPostgreSQL;

import ua.avm.sqlCMD.Commands;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class TempPostgreSQL {
    protected final String[] postgreSQL = "connect -pg -localhost -test -postgres -function root".split("\u0020"+"-");

    protected String insertRow = "new_field1=33|new_field2=value2|new_field3=value3";
    String updateRow = "new_field2=newValue|new_field2=value2";
    String deleteRow = "new_field2=newValue";

    protected String tableName = "new_tab";
    String newDbName = "new_db";

    private final String[][] columnsForNewTable = {
            {"new_field1", "integer", "y", "n"},
            {"new_field2", "varchar(20)", "n", "n"},
            {"new_field3", "varchar(10)", "n", "y"}
    };

    protected final ArrayList<String[]> tableColumns = new ArrayList<String[]>(){{
        add(columnsForNewTable[0]);
        add(columnsForNewTable[1]);
        add(columnsForNewTable[2]);
    }};
    protected HashMap<String,String> cmd;
    protected View view = new Console();
    protected DataBase db;

    public TempPostgreSQL() {

        cmd = new Commands().getCMD();

    }
}
