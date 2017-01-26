package ua.avm.sqlCMD.view;

import java.util.ArrayList;
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
    public void printTitle(String[] columnList, int sizeCol) {
        int countColumns = columnList.length;
        for (int i = 0; i < sizeCol*countColumns+3; i++) {
            this.write("_");
        }
        this.writeln("");
        for (int i = 0; i < countColumns; i++){

            this.write("|");
            this.fWriteln(columnList[i],sizeCol);
        }
        this.writeln("|");
        for (int i = 0; i < sizeCol*countColumns+3; i++) {
            this.write("=");
        }
        this.writeln("");


    }

    @Override
    public void printTableData(ArrayList<String[]> tableData, int sizeCol) {

        for (String[] oneRow: tableData){
            for (int i = 0; i < oneRow.length; i++) {

                this.write("|");
                this.fWriteln(oneRow[i],sizeCol);
            }
            this.write("|");
            this.writeln("");

        }

    }

    @Override
    public void printFooter(int sizeCol) {//todo входной параметр с количеством колонок заменить этим число 2 при расчете длины
        for (int i = 0; i < sizeCol*2+3; i++) {
            this.write("-");
        }
        System.out.println("");
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
