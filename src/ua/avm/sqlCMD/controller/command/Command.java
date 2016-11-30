package ua.avm.sqlCMD.controller.command;

/**
 * Created by AVM on 02.11.2016.
 */
public interface Command {

    boolean canDoIt(String command);

    void doIt(String[] command);



}
