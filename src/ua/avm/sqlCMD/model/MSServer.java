package ua.avm.sqlCMD.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//connect -ms -DBServer -avm -sa -SQL_master
public class MSServer extends DataBase{

    MSServer(String[] paramLine) throws Exception {
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
        String query = "SELECT name, suser_sname(owner_sid) FROM sys.databases WHERE database_id > 6";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()){
                result.put(resultSet.getString(1), resultSet.getString(2));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    @Override
    public boolean createDB(String dbName) {
        String query = "CREATE DATABASE "+dbName;
        try(Statement statement = connection.createStatement()) {
            return statement.execute(query);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isDataBaseExist(String dbName) {

        String query = "SELECT 1 FROM master.dbo.sysdatabases " +
                "as t WHERE t.name = '"+dbName+"'";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            return (resultSet.next())&&(resultSet.getBoolean(1));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean dropDB(String dbName) {
        String query = "DROP DATABASE "+dbName;
        try (Statement statement = connection.createStatement()) {
            return !statement.execute(query);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        String query = "DROP TABLE "+tableName;

        try(Statement statement = connection.createStatement()) {
            return !statement.execute(query);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, String> getListTable() {
        HashMap<String, String> result = new HashMap<>();
        String query = "use "+dbaseName+" SELECT sc.name +'.'+ ta.name TableName" +
                " ,SUM(pa.rows) RowCnt" +
                " FROM sys.tables ta" +
                " INNER JOIN sys.partitions pa" +
                " ON pa.OBJECT_ID = ta.OBJECT_ID" +
                " INNER JOIN sys.schemas sc" +
                " ON ta.schema_id = sc.schema_id" +
                " WHERE ta.is_ms_shipped = 0 AND pa.index_id IN (1,0)" +
                " GROUP BY sc.name,ta.name";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()){
                result.put(resultSet.getString(1), resultSet.getString(2));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    @Override
    public boolean isTableExist(String tableName) {

        String query = "use "+dbaseName+" IF EXISTS (SELECT 1 " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_TYPE='BASE TABLE' " +
                "AND TABLE_NAME='"+tableName+"') " +
                "SELECT 1 AS res ELSE SELECT 0 AS res";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean createTab(String tableName, ArrayList<String[]> columns) {
        String sql = "use "+dbaseName+" CREATE TABLE "+tableName+" ( ";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < columns.size(); i++){
            String[] oneColumn = columns.get(i);
            sql = sql.concat(stringBuilder.append(oneColumn[0]).append(" ").append(oneColumn[1]).append(" ").toString());
            stringBuilder.delete(0,stringBuilder.length());
            if (oneColumn[2].equals("y")){
                sql = sql.concat(" PRIMARY KEY ");
            }
            if (oneColumn[3].equals("n")){
                sql = sql.concat(" NOT NULL ");
            }else{
                sql = sql.concat(" NULL ");
            }

            if (i < columns.size() - 1){
                sql = sql.concat(",");
            }else{
                sql = sql.concat(")");
            }

        }
        try (Statement statement = connection.createStatement()){
            return  !statement.execute(sql);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }



    }

    @Override
    public ArrayList<String[]> viewTable(String[] commandLine) {

        String query = "use "+dbaseName+" select ";
        int length = commandLine.length;
        if(length == 3){
            query = query.concat(" TOP "+commandLine[2]);
        }else {
            if (length == 4) {

                int newLimit = Integer.parseInt(commandLine[2]) + Integer.parseInt(commandLine[3]);
                query = query.concat(" TOP " + Integer.toString(newLimit));
            }
        }
        query = query.concat(" * from "+commandLine[1]);

        ArrayList<String[]> dataByQuery = getDataByQuery(query);
        if (length == 4){

            for (int i = 0; i < Integer.parseInt(commandLine[3]); i++) {

                dataByQuery.remove(0);

            }

        }

        return dataByQuery;


    }

    @Override
    public String buildInsertQuery(String[] insertData, String tableName) {
        String firstPartOfQuery = "use "+dbaseName+" insert into dbo."+tableName+"(";
        String secondPartOfQuery = "values (";
        for (String anInsertData : insertData) {
            String[] tmp = anInsertData.split("\\=");
            firstPartOfQuery = firstPartOfQuery.concat(tmp[0] + ",");
            secondPartOfQuery = secondPartOfQuery.concat("'" + tmp[1] + "'" + ",");
        }
        firstPartOfQuery = firstPartOfQuery.substring(0,firstPartOfQuery.length()-1).concat(") ");
        secondPartOfQuery = secondPartOfQuery.substring(0,secondPartOfQuery.length()-1).concat(") ");

        return firstPartOfQuery.concat(secondPartOfQuery);
    }

    @Override
    public String buildDeleteQuery(String[] condition, String tableName) {
        return "delete from dbo."+ tableName + " where " + condition[0] + "='" + condition[1] + "'";
    }

    @Override
    public String buildUpdateQuery(String[] condition, String tableName) {
        String[] set = condition[0].split("\\=");
        String[] where = condition[1].split("\\=");

        return  "update dbo."+tableName+" set "+set[0]+"='"+set[1]+"' where "+where[0]+"='"+where[1]+"'";
    }

    @Override
    public String buildClearTabQuery(String tableName) {
        return "delete from dbo."+tableName;
    }

}

