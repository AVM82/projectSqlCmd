package ua.avm.sqlCMD.model;

import java.sql.*;
import java.util.ArrayList;

public abstract class DataBase implements DBManager{

    String userName;
    protected String password;

    String dbaseName = null;
    protected String port;
    protected String server;

    private boolean isConnect = false;

    Connection connection = null;
    String DBaseType = "> ";
    final String FIREBIRD = "FireBird> ";
    final String MSSQLSERVER = "MS SQL Server> ";
    final String POSTGRESQL = "PostgreSQL> ";
    private static  DataBase dataBase;
    final int NO_DB = 5; // count of parameters without database
    int index = 3; // index of dbName parameters

    public boolean isConnect() {
        try {
            return !connection.isClosed();
        } catch (SQLException message) {
            System.err.println("ERROR! \n"+ message);
            return true;
        }
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
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))  {

            ArrayList<String> rowData = new ArrayList<>();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            String[] columnName = new String[numberOfColumns];

            for (int i = 1; i <= numberOfColumns; i++) {
                columnName[i-1]=rsMetaData.getColumnLabel(i);
            }
            tableData.add(columnName);
            while (resultSet.next()){
                for (int i = 1; i <= numberOfColumns; i++) {
                    String cellValue = resultSet.getString(i);
                    if (cellValue == null){
                        cellValue = "";
                    }
                    rowData.add(cellValue.trim());
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
            System.err.println(e.getMessage());
        }
        finally {

            try {
                if (columns != null) {
                    columns.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return columnName.toArray(new String[columnName.size()]);

    }


    public void runQuery(String query) {

        try (Statement statement = connection.createStatement()){
            statement.execute(query);

            System.out.println("The command completed successfully!\n");


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }



}

