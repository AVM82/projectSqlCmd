package ua.avm.sqlCMD.controller;

import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.Connect;
import ua.avm.sqlCMD.controller.command.Disconnect;
import ua.avm.sqlCMD.controller.command.Exit;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;


/**
 * Created by AVM on 12.10.2016.
 */
public class Controller {
    DataBase db;
    private boolean connected = false;

    private final Command [] command;
    View view;

    public Controller(View view) {
        this.view = view;
        this.command = new Command[] { new Exit(view), new Connect(db, view), new Disconnect(view) };
    }

    public void run(View view){

        while(true){
            view.writeln("Enter the command or \"-?\" for help.");
            String[] inputCommand = view.read().split("\u0020"+"-");

            for (Command cmd: command) {
                if (cmd.canDoIt(inputCommand[0])) {
                    cmd.doIt(inputCommand);
                    break;
                }
            }
        }
    }





//    public boolean connect(View view, String line) {
//        if (line.equals("exit")){
//            new Exit(view).doIt(null);
//        }
//        db = DataBase.initDB(line);
//        if (db == null){
//            view.setPrefix("Try again> ");
//            return false;
//        }
//        view.setPrefix(db.getDBaseType());
//        return true;
//    }
}
