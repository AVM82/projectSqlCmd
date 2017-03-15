package ua.avm.sqlCMD.model;



import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        String query = "SELECT pg_database.datname, " +
                "pg_user.usename FROM pg_database, pg_user " +
                " WHERE pg_database.datdba = pg_user.usesysid and datistemplate = false";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

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

        try (Statement statement = connection.createStatement()){
            return statement.execute("CREATE DATABASE "+dbName+" WITH OWNER = "+userName);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean isDataBaseExist(String dbName) {
        String query = "SELECT 1 FROM pg_database WHERE datname = '"+dbName+"'";
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

        try (Statement statement = connection.createStatement()) {
            return statement.execute("DROP DATABASE "+dbName);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            return  statement.execute("DROP TABLE "+tableName);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, String> getListTable() {
        HashMap<String, String> result = new HashMap<>();
        ResultSet resultSet = null;
        try(Statement statement = connection.createStatement()) {

            resultSet = statement.executeQuery("SELECT relname FROM pg_stat_user_tables");
            ArrayList<String> tablesName = new ArrayList<>();
            while (resultSet.next()){

                tablesName.add(resultSet.getString(1));
            }
            for (String aTablesName : tablesName) {
                resultSet = statement.executeQuery("SELECT COUNT(*) from public." + aTablesName);
                resultSet.next();
                result.put(aTablesName, resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return result;


    }

    @Override
    public boolean isTableExist(String tableName) {
        String query = "SELECT EXISTS ( SELECT 1"+
                " FROM information_schema.tables WHERE  table_name = '"+tableName+"')";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean createTab(String tableName, ArrayList<String[]> columns) {
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
        try(Statement statement = connection.createStatement()) {
                return statement.execute(sql);

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
        return "delete from public."+ tableName + " where " + condition[0] + "='" + condition[1] + "'";
    }

    @Override
    public String buildUpdateQuery(String[] condition, String tableName) {
        String[] set = condition[0].split("\\=");
        String[] where = condition[1].split("\\=");

        return  "update public."+tableName+" set "+set[0]+"='"+set[1]+"' where "+where[0]+"='"+where[1]+"'";

    }

    @Override
    public String buildClearTabQuery(String tableName) {
        return "delete from public."+tableName;
    }


}
