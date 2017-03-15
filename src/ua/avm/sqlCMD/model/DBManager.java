package ua.avm.sqlCMD.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public interface DBManager {

    HashMap<String, String> getListDB();

    boolean createDB(String dbName);

    boolean isDataBaseExist(String dbName);

    boolean dropDB(String dbName);

    boolean dropTable(String tableName);

    Map<String,String> getListTable();

    boolean isTableExist (String tableName);

    boolean createTab(String tableName, ArrayList<String[]> columns);

    ArrayList<String[]> viewTable(String[] commandLine);

    String buildInsertQuery(String[] insertData, String tableName);

    String buildDeleteQuery(String[] condition, String tableName);

    String buildUpdateQuery(String[] condition, String tableName);

    String buildClearTabQuery(String tableName);
}
