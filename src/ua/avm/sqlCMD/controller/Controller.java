package ua.avm.sqlCMD.controller;

import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 12.10.2016.
 */
public class Controller {
    DataBase db;

    public void command(View view, String line){

        view.writeln("DONE!");

    }
    public boolean connect(View view, String line) {

        db = DataBase.initDB(line);
        if (db == null){
            view.setPrefix("Try again> ");//todo попав в Try again> не работает команда exit
            return false;
        }
        view.setPrefix(db.getDBaseType());
        return true;
    }
}
