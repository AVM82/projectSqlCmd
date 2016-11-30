package ua.avm.sqlCMD.controller;

import ua.avm.sqlCMD.controller.command.*;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;


/**
 * Created by AVM on 12.10.2016.
 */
public class Controller {

    private boolean connected = false;
    DataBase db;
    View view;

    public Controller(View view) {
        this.view = view;
    }

    public void run(View view) {

        while(true){
            String[] inputCommand;


            if ((db == null) || (!db.isConnect()) ) {
                view.writeln("You need to connect to the database to continue");
                view.writeln("DBMS:\n-fb\tFireBird (DB_Name = full_path\\DB_Name.fdb)\n-ms\tMS SQL Server\n-pg\tPostgreSQL");
                view.writeln("For connect to DB please enter: connect -DBMS -DB_Server[:port] [-DB_Name] -user -password");
                inputCommand = view.read().split("\u0020"+"-");
                db = new Connect(view).getDb(inputCommand);
                continue;
            }else{
                view.writeln("Enter the command or \"-?\" for help.");
                inputCommand = view.read().split("\u0020"+"-");
            }

            final Command[] command = new Command[]{    new Exit(view),
                                                        new Disconnect(db, view),
                                                        new ListDB(db, view)
                                                    };

            for (Command cmd: command) {
                if (cmd.canDoIt(inputCommand[0])) {
                    cmd.doIt(inputCommand);
                    break;
                }
            }
        }
    }
}
