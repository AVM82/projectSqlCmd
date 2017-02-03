package ua.avm.sqlCMD.model;

import org.firebirdsql.management.FBManager;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AVM on 12.10.2016.
 */
//connect -fb -DBServer:3050 -D:/Andromeda/TestDB/sqlCMD.FDB -SYSDBA -masterkey

public class DBFireBird extends DataBase{

    public DBFireBird(String[] paramLine) throws Exception {

        String[] dbs = paramLine[2].split(":");
        server = dbs[0];
        if (dbs.length > 1){
            port = dbs[1];
        }else{
            port = "3050";
        }

        if (paramLine.length > NO_DB){
            dbaseName = paramLine[index++];

        }
        else{
            throw new Exception("Not supported.");
        }

        userName = paramLine[index++];
        password = paramLine[index];

        Class.forName("org.firebirdsql.jdbc.FBDriver");
        connection = DriverManager.getConnection("jdbc:firebirdsql:"+server+"/"+port + ":"+dbaseName,
                userName,password);

        DBaseType = FIREBIRD;

    }

    @Override
    public HashMap<String, String> getListDB() {
        //not supported
        return null;
    }

    @Override
    public boolean createDB(String dbName) {
        try {

            FBManager fbManager = new FBManager();
            fbManager.setServer(server);
            fbManager.setPort(Integer.valueOf(port));
            fbManager.setUserName("sysdba");
            fbManager.setPassword("masterkey");
            fbManager.start();
            fbManager.createDatabase(dbName,userName, password);
            fbManager.stop();
            return true;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean isDataBaseExist(String dbName) {
    try{
        FBManager fbManager = new FBManager();
        fbManager.setServer(server);
        fbManager.setPort(Integer.valueOf(port));
        fbManager.setUserName("sysdba");
        fbManager.setPassword("masterkey");
        fbManager.start();
        boolean exist = fbManager.isDatabaseExists(dbName,userName, password);
        fbManager.stop();
        return exist;

    } catch (Exception e) {
        System.err.println(e.getMessage());
        return false;
    }
    }

    @Override
    public boolean dropDB(String dbName){
        try{

            FBManager fbManager = new FBManager();
            fbManager.setServer(server);
            fbManager.setPort(Integer.valueOf(port));
            fbManager.setUserName("sysdba");
            fbManager.setPassword("masterkey");
            fbManager.start();
            fbManager.dropDatabase(dbName,userName, password);
            fbManager.stop();
            return true;

        } catch(Exception e) {
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
            ResultSet resultSet = statement.executeQuery("EXECUTE BLOCK " +
                    "returns ( tab varchar(30), cnt integer, stm varchar(60) ) " +
                    "as " +
                    "BEGIN " +
                    "for select trim(r.RDB$RELATION_NAME), cast('select count(*) from \"'||trim(r.RDB$RELATION_NAME)||'\"' as varchar(60)) " +
                    "from RDB$RELATIONS r " +
                    "where (r.RDB$SYSTEM_FLAG is null or r.RDB$SYSTEM_FLAG = 0) and r.RDB$VIEW_BLR is null " +
                    "order by 1 " +
                    "into :tab, :stm " +
                    "DO " +
                    "BEGIN " +
                    "execute statement :stm into :cnt; " +
                    "suspend; " +
                    "END " +
                    "END ");

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
            ResultSet resultSet = connection.createStatement().executeQuery("select COUNT(*) from rdb$relations where rdb$relation_name = '"+tableName+"'");
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    @Override
    public boolean createTab(String tableName, ArrayList<String[]> columns) {

        try {
            String sql = "CREATE TABLE "+tableName+" ( ";
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < columns.size(); i++){
                String[] oneColumn = columns.get(i);
                sql = sql.concat(stringBuilder.append(oneColumn[0]).append(" ").append(oneColumn[1]).append(" ").toString());
                stringBuilder.delete(0,stringBuilder.length());
                if (oneColumn[3].equals("n")){
                    sql = sql.concat(" NOT NULL ");
                }
                if (oneColumn[2].equals("y")){
                    sql = sql.concat(" PRIMARY KEY ");
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

        String query = "select ";
        int length = commandLine.length;
        if(length == 3){
            query = query.concat(" FIRST "+commandLine[2]);
        }else {
            if (length == 4) {
                query = query.concat(" FIRST " + commandLine[2] + " SKIP " + commandLine[3]);
            }
        }
        query = query.concat(" * from "+commandLine[1]);

        return getDataByQuery(query);
    }

    @Override
    public String buildInsertQuery(String[] insertData, String tableName) {
        String firstPartOfQuery = "insert into "+tableName+"(";
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
        return "delete from "+ tableName + " where " + condition[0] + "='" + condition[1] + "'";
    }

    @Override
    public String buildUpdateQuery(String[] condition, String tableName) {
        String[] set = condition[0].split("\\=");
        String[] where = condition[1].split("\\=");

        return  "update "+tableName+" set "+set[0]+"='"+set[1]+"' where "+where[0]+"='"+where[1]+"'";
    }

}

