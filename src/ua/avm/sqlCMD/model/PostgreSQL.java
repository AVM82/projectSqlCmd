package ua.avm.sqlCMD.model;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by AVM on 12.10.2016.
 */
//connect -pg -localhost -test -postgres -function root
public class PostgreSQL extends DataBase{

    public PostgreSQL(String[] paramLine) throws Exception {
        String[] dbs = paramLine[2].split(":");
        server = dbs[0];
        if (dbs.length > 1){
            port = dbs[1];
        }else{
            port = "5432";
        }

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
        DBaseType = POSTGRESQL;

    }

    @Override
    public HashMap<String, String> getListDB(){
        HashMap<String, String> result = new HashMap<>();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT pg_database.datname, " +
                    "pg_user.usename FROM pg_database, pg_user " +
                    " WHERE pg_database.datdba = pg_user.usesysid and datistemplate = false");

            while (resultSet.next()){
                result.put(resultSet.getString(1), resultSet.getString(2));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean createDB(String dbName) {
        try {
            connection.createStatement().execute("CREATE DATABASE "+dbName+" WITH OWNER = "+userName);
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }
}
