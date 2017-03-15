package ua.avm.sqlCMD.controller.command;

public interface Command {

    boolean canDoIt(String command);

    void doIt(String[] command);



}
