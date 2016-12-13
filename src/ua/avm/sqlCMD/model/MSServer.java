package ua.avm.sqlCMD.model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AVM on 11.10.2016.
 */
//connect -ms -DBServer -Test -sa -SQL_master
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
            dbaseName = paramLine[index++];
        }
        else{
            dbaseName = null;
        }

        userName = paramLine[index++];
        password = paramLine[index];
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection("jdbc:sqlserver://"+server+":"+port,
                userName,password);
        DBaseType = MSSQLSERVER;
    }

    @Override
    public HashMap<String, String> getListDB() {

        HashMap<String, String> result = new HashMap<>();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name, suser_sname(owner_sid) FROM sys.databases WHERE database_id > 6");

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
            connection.createStatement().execute("CREATE DATABASE "+dbName);
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isDataBaseExist(String dbName) {

        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT 1 FROM master.dbo.sysdatabases " +
                    "as t WHERE t.name = '"+dbName+"'");
            if((resultSet.next())&&(resultSet.getBoolean(1))){
                return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean dropDB(String dbName) {
        try {
            connection.createStatement().execute("DROP DATABASE "+dbName);
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, String> getListTable() {
        HashMap<String, String> result = new HashMap<>();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("use "+dbaseName+" SELECT sc.name +'.'+ ta.name TableName" +
                    " ,SUM(pa.rows) RowCnt" +
                    " FROM sys.tables ta" +
                    " INNER JOIN sys.partitions pa" +
                    " ON pa.OBJECT_ID = ta.OBJECT_ID" +
                    " INNER JOIN sys.schemas sc" +
                    " ON ta.schema_id = sc.schema_id" +
                    " WHERE ta.is_ms_shipped = 0 AND pa.index_id IN (1,0)" +
                    " GROUP BY sc.name,ta.name");

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

}

