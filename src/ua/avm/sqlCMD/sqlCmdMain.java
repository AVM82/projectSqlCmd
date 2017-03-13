package ua.avm.sqlCMD;

import ua.avm.sqlCMD.controller.Controller;
import ua.avm.sqlCMD.view.Console;

/**
 * Created by AVM on 11.10.2016.
 */
public class sqlCmdMain {


    public static void main(String[] args) {

        Console view = new Console();
        Controller ctrl = new Controller(view);
        ctrl.run(view);
    }
}





