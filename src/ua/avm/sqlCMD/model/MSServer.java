package ua.avm.sqlCMD.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

/**
 * Created by AVM on 11.10.2016.
 */
public class MSServer extends DataBase{

    public MSServer(String[] paramLine) throws Exception {
        String[] dbs = paramLine[2].split(":");
        server = dbs[0];
        if (dbs.length > 1){
            port = dbs[1];
        }else{
            port = "1433";
        }
        if (paramLine.length > NO_DB){
            dbaseName = ";databaseName=" + paramLine[index++];
        }
        else{
            dbaseName = "";
        }

        userName = paramLine[index++];
        password = paramLine[index];
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection("jdbc:sqlserver://"+server+":"+port,
                userName,password);
        DBaseType = "MS SQL Server> ";
    }

    @Override
    public HashMap<String, String> getListDB() {
        return null;
    }
}
