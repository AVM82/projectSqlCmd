package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 07.11.2016.
 */
public class Connect implements Command {

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

        if (command[0].equals("exit")){
            new Exit(view).doIt(null);
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
