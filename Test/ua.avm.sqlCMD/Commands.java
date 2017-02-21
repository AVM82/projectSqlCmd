package ua.avm.sqlCMD;

import java.util.HashMap;

/**
 * Created by AVM on 20.02.2017.
 */
public class Commands {
    private HashMap<String,String> commands = new HashMap<>();

    public Commands() {
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
    }
    public HashMap<String,String> getCMD(){
        return commands;
    }
}
