package ua.avm.sqlCMD.view;

import java.util.*;

/**
 * Created by AVM on 12.10.2016.
 */
public class Console implements View {

    private String prefix = "> ";

    @Override
    public boolean requestForConfirmation(){
        warningWriteln("Are you sure? (y/n)");
        return read().equals("y");
    }


    @Override
    public void writeln(String message) {

        System.out.println("\u001B[32m" + message);
//        System.out.println(message);

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
        try
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print(prefix);
            return scanner.nextLine();
        }
        catch (NoSuchElementException e){
            return null;
        }
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
    public void printFooter(int sizeCol) {
        for (int i = 0; i < sizeCol+3; i++) {
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

    public void printTableData(Map<String, String> data, String[] title, int sizeCol){

        if (data == null) {
            this.warningWriteln("Command not supported for this database.");
        }else{

            Set<Map.Entry<String, String>> set = data.entrySet();

            this.printTitle(title, sizeCol);

            for (Map.Entry<String, String> value : set) {

                String string1 = value.getKey();
                String string2 = value.getValue();
                this.write("|");
                this.fWriteln(string1,sizeCol);
                this.write("|");
                this.fWriteln(string2,sizeCol);
                this.write("|");
                System.out.println("");
            }


            this.printFooter(title.length*sizeCol);
        }


    }
}
