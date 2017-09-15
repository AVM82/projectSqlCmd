package ua.avm.sqlCMD;

import ua.avm.sqlCMD.controller.Controller;
import ua.avm.sqlCMD.view.Console;


public class sqlCmdMain {


    public static void main(String[] args) {

        Console view = new Console();
        Controller ctrl = new Controller(view);
        ctrl.run(view);
    }


}





