package ua.avm.sqlCMD.model;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by AVM on 11.10.2016.
 */
public class MSServer extends DataBase{
    //connect -ms -DBServer -Test -sa -SQL_master

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



        HashMap<String, String> result = new HashMap<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name, suser_sname(owner_sid) as \"owner\" FROM sys.databases WHERE database_id > 6");

            while (resultSet.next()){
                result.put(resultSet.getString("name"), resultSet.getString("owner"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}

