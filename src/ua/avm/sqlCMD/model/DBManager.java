package ua.avm.sqlCMD.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by AVM on 12.10.2016.
 */
public interface DBManager {

    HashMap<String, String> getListDB();

    boolean createDB(String dbName);

    boolean isDataBaseExist(String dbName);


    boolean dropDB(String dbName);
}
