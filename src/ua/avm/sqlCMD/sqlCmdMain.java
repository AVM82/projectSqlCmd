package ua.avm.sqlCMD;

import ua.avm.sqlCMD.controller.Controller;
import ua.avm.sqlCMD.view.Console;
import ua.avm.sqlCMD.view.View;

/**
 * Created by AVM on 11.10.2016.
 */
public class sqlCmdMain {

    public static void main(String[] args) {

        Controller ctrl = new Controller();
        View view = new Console();
        view.writeln("Connect to DataBase");
        view.writeln("DBMS:\n-fb\tFireBird (DB_Name = full_path\\DB_Name.fdb)\n-ms\tMS SQL Server\n-pg\tPostgreSQL");
        view.writeln("For connect to DB please enter: -DBMS -DB_Server[:port] [-DB_Name] -user -password");

        while(!ctrl.connect(view, view.read())){

            view.writeln("Server connection is not established.");

        }

        String command = "";


        while(!command.equals("exit")) {
            command = view.read();
            ctrl.command(view, command);
           }

    }
}
