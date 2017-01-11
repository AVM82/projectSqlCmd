package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 07.11.2016.
 */
public class Connect implements Command {

    private String COMMAND_SAMPLE = "connect -DBMS -DB_Server[:port] -DB_Name -user -password";
    private final View view;
    private DataBase db;

    public Connect(View view) {

        this.view = view;
    }

    @Override
    public boolean canDoIt(String command) {

        return "connect".equals(command);
    }

    @Override
    public void doIt(String[] command) {

        db = this.getDb(command);

    }

    public DataBase getDb(String[] command) {

        int countSample = Utility.countOfParam(COMMAND_SAMPLE,view.getCommandDelimiter());
        int countCommand = command.length - 1;
        if (command[0].equals("exit")){
            new Exit(view).doIt(command);
            return null;
        }
        if ((countCommand != countSample)&&(countCommand != countSample - 1)){
            view.warningWriteln(String.format("Invalid number of parameters. Expected %s or %s, but got %s",
                    countSample, countSample - 1 , countCommand));
            return null;
        }
        if (canDoIt(command[0])){

            db = DataBase.initDB(command);
        }else{
            view.warningWriteln("Error command.");
        }
        if (db == null){
            view.setPrefix("Try again> ");
            return null;
        }
        view.setPrefix(db.getDBaseType());
        return db;



    }
}
