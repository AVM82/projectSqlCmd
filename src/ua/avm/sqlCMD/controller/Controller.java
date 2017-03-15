package ua.avm.sqlCMD.controller;

import ua.avm.sqlCMD.controller.command.*;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.HashMap;

public class Controller {

    private DataBase db;
    private View view;
    private HashMap<String,String> commands = new HashMap<>();


    public Controller(View view) {

        this.view = view;
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

    public void run(View view) {
        try {
            while (true) {

                String[] inputCommand;
                if ((db == null) || (!db.isConnect())) {
                    view.writeln("");
                    view.writeln("***********************************************************************************************");
                    view.writeln("You need to connect to the database to continue");
                    view.writeln("DBMS:");
                    view.writeln("\t-fb FireBird (DB_Name = full_path\\DB_Name.fdb)");
                    view.writeln("\t-ms MS SQL Server");
                    view.writeln("\t-pg PostgreSQL");
                    view.writeln("For connect to DB please enter: connect -DBMS -DB_Server[:port] [-DB_Name] -user -password");
                    view.writeln("***********************************************************************************************");
                    view.writeln("");
                    inputCommand = getCommand(view);
                    db = new Connect(view, commands.get("Command connect to the database.")).getDb(inputCommand);
                    continue;
                } else {
                    view.writeln("Enter the command or 'help' for help.");
                    inputCommand = getCommand(view);

                }


                final Command[] command = new Command[]{
                        new Exit(view, commands.get("Exit the program.")),
                        new Disconnect(db, view, commands.get("Disconnecting from the server.")),
                        new ListDB(db, view, commands.get("Command lists the databases.")),
                        new CreateDB(db, view, commands.get("Command creates a new database.")),
                        new DropDB(db, view, commands.get("Command delete database.")),
                        new ListTab(db, view, commands.get("This command lists all the tables in the database.")),
                        new CreateTab(db, view, commands.get("Command creates a new table.")),
                        new DeleteTab(db, view, commands.get("Command delete table.")),
                        new ViewTable(db, view, commands.get("Command to view the contents of the table.")),
                        new InsertRow(db, view, commands.get("Command to insert a row into the table.")),
                        new DeleteRow(db, view, commands.get("Command to delete a row in the table.")),
                        new UpdateRow(db, view, commands.get("Command to update a row in the table.")),
                        new RunQuery(db, view, commands.get("Command executes query.")),
                        new Help(view, commands, commands.get("Command to view help.")),
                        new Clear(db, view, commands.get("Command clears the table.")),
                        new UnknownCommand(view, commands, commands.get("Command to view help."))
                };

                for (Command cmd : command) {
                    if (cmd.canDoIt(inputCommand[0])) {
                        cmd.doIt(inputCommand);
                        break;
                    }
                }
            }
        }
        catch (ExitException e){
            //do nothing
        }
    }

    private String[] getCommand(View view) {
        String line;
        String[] inputCommand = new String[0];
        line = view.read();
        if (line == null){
            new Exit(view,commands.get("Exit the program.")).doIt(new String[] {commands.get("Exit the program.")});
        }
        if (line != null) {
            inputCommand = line.split(view.getCommandDelimiter());
        }
        return inputCommand;
    }
}
