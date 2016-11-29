package ua.avm.sqlCMD.model;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by AVM on 12.10.2016.
 */
public class PostgreSQL extends DataBase{

    public PostgreSQL(String[] paramLine) throws Exception {
        String[] dbs = paramLine[2].split(":");
        server = dbs[0];
        if (dbs.length > 1){
            port = dbs[1];
        }else{
            port = "5432";
        }
//        int index = 3;

        if (paramLine.length > NO_DB){
            dbaseName = paramLine[index++];
        }
        else{
            dbaseName = "";
        }
        userName = paramLine[index++];
        password = paramLine[index];
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://"+server+":"+port+"/"+dbaseName,userName,password);
        DBaseType = "PostgreSQL> ";

        //connect -pg -localhost -test -postgres -function root
    }

    @Override
    public HashMap<String, String> getListDB(){
        HashMap<String, String> result = new HashMap<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT pg_database.datname as \"Database\", " +
                    "pg_user.usename as \"Owner\" FROM pg_database, pg_user " +
                    " WHERE pg_database.datdba = pg_user.usesysid and datistemplate = false");

            while (resultSet.next()){
                result.put(resultSet.getString("Database"), resultSet.getString("Owner"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
