package ua.avm.sqlCMD.model;

import org.firebirdsql.management.FBManager;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            //TODO query to user for create database
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
}
