package ua.avm.sqlCMD.controller;

import java.util.HashMap;


public final class Commands {
    private final static HashMap<String,String> commands = new HashMap<>();

    static {
        commands.put("Exit the program.","exit");
        commands.put("Disconnecting from the server.","disconnect");
        commands.put("Command lists the databases.","ldb");
        commands.put("Command creates a new database.","createdb -dbName");
        commands.put("Command delete database.","dropdb -dbName");
        commands.put("This command lists all the tables in the database.","tables");
        commands.put("Command creates a new table.","create -tableName");
        commands.put("Command delete table.","drop -tableName");
        commands.put("Command to view the contents of the table.","find -tableName -limit -offset");
        commands.put("Command to insert a row into the table.","insert -tableName");
        commands.put("Command to delete a row in the table.","delete -tableName");
        commands.put("Command to update a row in the table.","update -tableName");
        commands.put("Command executes query.","query -queryLine");
        commands.put("Command connect to the database.","connect -DBMS -DB_Server[:port] -DB_Name -user -password");
        commands.put("Command to view help.","help");
        commands.put("Command clears the table.","clear -tableName");
    }
    public static HashMap<String,String> getCMD(){
        return commands;
    }
}
