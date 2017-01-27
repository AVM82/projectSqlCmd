package ua.avm.sqlCMD.model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by AVM on 12.10.2016.
 */
public abstract class DataBase implements DBManager{

    protected String userName;
    protected String password;

    public String getDbaseName() {
        return dbaseName;
    }

    protected String dbaseName = null;
    protected String port;
    protected String server;

    private boolean isConnect;

    Connection connection = null;
    String DBaseType = "> ";
    final String FIREBIRD = "FireBird> ";
    final String MSSQLSERVER = "MS SQL Server> ";
    final String POSTGRESQL = "PostgreSQL> ";
    private static  DataBase dataBase;
    protected final int NO_DB = 5; // count of parameters without database
    protected int index = 3; // index of dbName parameters

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }


    public static DataBase initDB(String[] paramLine){

        try{
            //connect to FireBird
            if (paramLine[1].equals("fb")){
                dataBase = new DBFireBird(paramLine);

            }else {

                //connect to MS SQL Server
                if (paramLine[1].equals("ms")){
                    dataBase = new MSServer(paramLine);
                }else {

                    //connect to PostgreSQL
                    if (paramLine[1].equals("pg")){
                        dataBase = new PostgreSQL(paramLine);
                    }else{
                        throw new Exception("\u001B[31m"+"DBMS is selected incorrectly");
                    }
                }
            }
            dataBase.setConnect(true);
            System.out.println("The connection to the server has been established!");
        }
        catch (Exception e){
            String message = e.getMessage();

            System.err.println("Connection error. \n"+ message);
        }

        return dataBase;
    }

    public String getDBaseType() {
        return DBaseType;
    }


    public void closeConnection() {

        try {

            connection.close();
            dataBase.setConnect(false);
            dataBase.dbaseName = null;
            DBaseType = "> ";
        } catch (SQLException e) {
            String message = e.getMessage();

            System.out.println("Close connection error. \n"+ message);
        }

    }

    public ArrayList<String[]> getDataByQuery(String query){

        ArrayList<String[]> tableData = new ArrayList<>();
        try(ResultSet resultSet = connection.createStatement().executeQuery(query))  {
            ArrayList<String> rowData = new ArrayList<>();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            while (resultSet.next()){
                for (int i = 1; i <= numberOfColumns; i++) {
                    rowData.add(resultSet.getString(i));
                }
                tableData.add(rowData.toArray(new String[rowData.size()]));
                rowData.clear();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return tableData;

    }

    public String[] getColumnList(String tableName) {

        ArrayList<String> columnName = new ArrayList<>();
        DatabaseMetaData databaseMetaData;
        ResultSet columns = null;
        try {
            databaseMetaData = connection.getMetaData();
            columns = databaseMetaData.getColumns(dbaseName,null,tableName,null);
            while(columns.next()){
                columnName.add(columns.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            try {
                columns.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return columnName.toArray(new String[columnName.size()]);

    }


}

