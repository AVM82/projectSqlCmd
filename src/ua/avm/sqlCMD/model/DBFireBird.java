package ua.avm.sqlCMD.model;

import org.firebirdsql.management.FBManager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by AVM on 12.10.2016.
 */
//connect -fb -DBServer:3050 -D:/Andromeda/TestDB/DBase.FDB -SYSDBA -masterkey
//connect -fb -DBServer:3050 -SYSDBA -masterkey

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
}
