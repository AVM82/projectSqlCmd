package ua.avm.sqlCMD.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AVM on 12.10.2016.
 */
public interface DBManager {

    HashMap<String, String> getListDB();

    boolean createDB(String dbName);

    boolean isDataBaseExist(String dbName);

    boolean dropDB(String dbName);

    Map<String,String> getListTable();

    boolean isTableExist (String tableName);

    boolean createTab(String tableName, ArrayList<String[]> columns);
}
