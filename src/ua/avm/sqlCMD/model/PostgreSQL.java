package ua.avm.sqlCMD.model;



import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public boolean isDataBaseExist(String dbName) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT 1 FROM pg_database WHERE datname = '"+dbName+"'");
            return (resultSet.next())&&(resultSet.getBoolean(1));

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
    public boolean dropTable(String tableName) {
        try {
            connection.createStatement().execute("DROP TABLE "+tableName);
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
            ResultSet resultSet = statement.executeQuery("SELECT relname,n_live_tup + 1 FROM pg_stat_user_tables");

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
    public boolean isTableExist(String tableName) {

        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT EXISTS ( SELECT 1"+
                    " FROM information_schema.tables WHERE  table_name = '"+tableName+"')");
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean createTab(String tableName, ArrayList<String[]> columns) {
//todo all of try...catch change to try-with-resources
        try {
            String sql = "CREATE TABLE "+tableName+" ( ";
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < columns.size(); i++){
                String[] oneColumn = columns.get(i);
                sql = sql.concat(stringBuilder.append(oneColumn[0]).append(" ").append(oneColumn[1]).append(" ").toString());
                stringBuilder.delete(0,stringBuilder.length());
                if (oneColumn[2].equals("y")){
                    sql = sql.concat("PRIMARY KEY ");
                }
                if (oneColumn[3].equals("n")){
                    sql = sql.concat("NOT NULL ");
                }
                if (i < columns.size() - 1){
                    sql = sql.concat(",");
                }else{
                    sql = sql.concat(")");
                }

            }
                connection.createStatement().execute(sql);
                return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<String[]> viewTable(String[] commandLine) {


        String query = "select * from public."+commandLine[1];
        int length = commandLine.length;
        if(length == 3){
            query = query.concat(" LIMIT "+commandLine[2]);
        }else {
            if (length == 4) {
                query = query.concat(" LIMIT " + commandLine[2] + " OFFSET " + commandLine[3]);
            }
        }

        return getDataByQuery(query);
    }

    @Override
    public String buildInsertQuery(String[] insertData, String tableName) {
        String firstPartOfQuery = "insert into public."+tableName+"(";
        String secondPartOfQuery = "values (";
        for (int i = 0; i < insertData.length; i++){
            String[] tmp = insertData[i].split("\\=");
            firstPartOfQuery = firstPartOfQuery.concat(tmp[0]+",");
            secondPartOfQuery = secondPartOfQuery.concat("'"+tmp[1]+"'"+",");
        }
        firstPartOfQuery = firstPartOfQuery.substring(0,firstPartOfQuery.length()-1).concat(") ");
        secondPartOfQuery = secondPartOfQuery.substring(0,secondPartOfQuery.length()-1).concat(") ");

        return firstPartOfQuery.concat(secondPartOfQuery);

    }

    @Override
    public String buildDeleteQuery(String[] condition, String tableName) {
        return "delete from public."+ tableName + " where " + condition[0] + "='" + condition[1] + "'";
    }

    @Override
    public String buildUpdateQuery(String[] condition, String tableName) {
        String[] set = condition[0].split("\\=");
        String[] where = condition[1].split("\\=");

        return  "update public."+tableName+" set "+set[0]+"='"+set[1]+"' where "+where[0]+"='"+where[1]+"'";

    }

}
