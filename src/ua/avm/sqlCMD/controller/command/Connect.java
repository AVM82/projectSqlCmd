package ua.avm.sqlCMD.controller.command;

import ua.avm.sqlCMD.controller.Utility;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 07.11.2016.
 *
 *
 */
public class Connect implements Command {

    private final String COMMAND_SAMPLE;
    private View view;
    private DataBase db;

    public Connect(View view, String COMMAND_SAMPLE) {

        this.view = view;
        this.COMMAND_SAMPLE = COMMAND_SAMPLE;
    }

    @Override
    public boolean canDoIt(String command) {

        return COMMAND_SAMPLE.substring(0,COMMAND_SAMPLE.indexOf(view.getCommandDelimiter())).equals(command);

    }

    @Override
    public void doIt(String[] command) {

        db = getDb(command);

    }

    public DataBase getDb(String[] command) {

        if (command[0].equals("exit")){
            new Exit(view,"exit").doIt(command);
        }

        int countSample = Utility.countOfParam(COMMAND_SAMPLE,view.getCommandDelimiter());
        int countCommand = command.length - 1;

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

        if(db != null){
            view.setPrefix(db.getDBaseType());
        }else{
            view.setPrefix("Try again> ");
        }
        return db;
    }
}
