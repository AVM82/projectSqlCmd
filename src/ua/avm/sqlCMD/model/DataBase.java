package ua.avm.sqlCMD.model;

import java.sql.Connection;
import java.sql.SQLException;

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
    private static  DataBase dataBase;
    protected final int NO_DB = 5; // count of parameters without database
    protected int index = 3; // index of dbName parameters


    public static DataBase initDB(String[] paramLine){
//        String[] paramLine = url.split("\u0020"+"-");

        try{
            //connect to FireBird
            if (paramLine[1].equals("fb")){
                dataBase = new DBFireBird(paramLine);
            }
            //connect to MS SQL Server
            if (paramLine[1].equals("ms")){
                 dataBase = new MSServer(paramLine);
            }
            //connect to PostgreSQL
            if (paramLine[1].equals("pg")){
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


    public void closeConnection() {

        try {

            connection.close();
            DBaseType = "> ";
        } catch (SQLException e) {
            String message = e.getMessage();

            System.out.println("Close connection error. \n"+ message);
        }

    }


    public Connection getConnection() {
        return connection;
    }
}

