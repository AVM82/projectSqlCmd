package ua.avm.sqlCMD.model;

import java.sql.Connection;

/**
 * Created by AVM on 12.10.2016.
 */
public abstract class DataBase implements DBManager{

    protected String userName;
    protected String password;
    protected String dbaseName;
    protected String port;
    protected String server;
    Connection connection = null;
    protected String DBaseType = "> ";
    private static DataBase dataBase;
    protected final int NO_DB = 4; // count of parameters without database
    protected int index = 2; // index of dbName parameters


    public static DataBase initDB(String url){
        String[] paramLine = url.split("\u0020"+"-");

        try{
            //connect to FireBird
            if (paramLine[0].equals("-fb")){
                dataBase = new DBFireBird(paramLine);
            }
            //connect to MS SQL Server
            if (paramLine[0].equals("-ms")){
                 dataBase = new MSServer(paramLine);
            }
            //connect to PostgreSQL
            if (paramLine[0].equals("-pg")){
                 dataBase = new PostgreSQL(paramLine);
            }

            if (dataBase == null){
                throw new Exception("\u001B[31m"+"DBMS is selected incorrectly");
            }

            System.out.println("The connection to the server has been established!");
        }
        catch (Exception e){
            String message = e.getMessage();

            System.out.println("Connection error. \n"+ message);
        }

        return dataBase;
    }

    public String getDBaseType() {
        return DBaseType;
    }


}

