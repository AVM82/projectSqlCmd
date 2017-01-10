package ua.avm.sqlCMD.view;

import java.util.Scanner;

/**
 * Created by AVM on 12.10.2016.
 */
public class Console implements View {

    private String prefix = "> ";


    @Override
    public void writeln(String message) {

        System.out.println("\u001B[32m" + message);

    }

    @Override
    public void warningWriteln(String message) {

        System.out.println("\u001B[31m" + message);

    }

    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void fWriteln(String message, int length) {
        System.out.format("%"+Integer.toString(length)+"s" , message);

    }


    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prefix);
        return scanner.nextLine();
    }
    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getCommandDelimiter() {
        return COMMAND_DELIMITER;
    }

    @Override
    public String getSecondaryDelimiter() {
        return SECONDARY_DELIMITER;
    }


}
