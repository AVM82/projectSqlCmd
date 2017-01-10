package ua.avm.sqlCMD.controller;

import java.lang.Object;

import ua.avm.sqlCMD.view.View;

import java.util.Map;
import java.util.Set;

/**
 * Created by AVM on 06.12.2016.
 */
public class Utility {

    public static void printTab(Map<String, String> data, View view, String[] title, int sizeCol){

        if (data == null) {
            view.warningWriteln("Command not supported for this database.");
        }

        Set<Map.Entry<String, String>> set = data.entrySet();

        for (int i = 0; i < sizeCol*2+3; i++) {
            view.write("_");
        }
        view.writeln("");
        view.write("|");
        view.fWriteln(title[0],sizeCol);
        view.write("|");
        view.fWriteln(title[1],sizeCol);
        view.writeln("|");
        for (int i = 0; i < sizeCol*2+3; i++) {
            view.write("=");
        }
        view.writeln("");
        for (Map.Entry<String, String> value : set) {

            String string1 = value.getKey();
            String string2 = value.getValue();
            view.write("|");
            view.fWriteln(string1,sizeCol);
            view.write("|");
            view.fWriteln(string2,sizeCol);
            view.write("|");
            System.out.println("");
        }
        for (int i = 0; i < sizeCol*2+3; i++) {
            view.write("-");
        }
        System.out.println("");

    }

    public static void printTab(String[][] data, View view){

    }

    public static boolean verifyName(String name){
        return Character.isDigit(name.charAt(0));
    }
    public static int countOfParam(String sample, String delimiter) {
        return sample.split(delimiter).length;
    }

    public static boolean verifyParams(String sample,String delimiter, int countInputParams, View view){
        int countOfParam = Utility.countOfParam(sample, delimiter) - 1;
        if (countOfParam != countInputParams)
        {
            view.warningWriteln(String.format("Invalid number of parameters. Expected %s, but got %s",
                    countOfParam, countInputParams));
            return false;
        }else{
            return true;
        }


    }

    public static boolean requestForConfirmation(View view, String objForDel){
        view.warningWriteln("Are you sure to delete table "+objForDel+" (y/n)");
        if (view.read().equals("y")){
            return true;
        }else{
            return false;
        }
    }

}
