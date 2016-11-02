package ua.avm.sqlCMD.controller;

import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.Exit;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import static java.lang.System.exit;

/**
 * Created by AVM on 12.10.2016.
 */
public class Controller {
    DataBase db;

    private final Command [] command;
    private View view;


    public Controller(View view) {
        this.view = view;
        this.command = new Command[] { new Exit(view) };

    }

    public void run(View view){

        while(true){
            view.writeln("Enter the command or \"-?\" for help.");
            String inputCommand = view.read();

            for (Command cmd: command) {
                if (cmd.canDoIt(inputCommand)) {
                    cmd.doIt(inputCommand);
                    break;
                }
                
            }


        }
    }


    public boolean connect(View view, String line) {
        if (line.equals("exit")){
            exit(1);
        }
        db = DataBase.initDB(line);
        if (db == null){
            view.setPrefix("Try again> ");
            return false;
        }
        view.setPrefix(db.getDBaseType());
        return true;
    }
}
