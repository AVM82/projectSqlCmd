package ua.avm.sqlCMD.controller;

import ua.avm.sqlCMD.view.View;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by AVM on 06.12.2016.
 */
public class Utility {

    public static void printTab(Map<String, String> data, View view, String[] title, int sizeCol){

        if (data == null) {
            view.warningWriteln("Command not supported for this database.");
        }else{

            Set<Map.Entry<String, String>> set = data.entrySet();

            view.printTitle(title, sizeCol);

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


            view.printFooter(title.length*sizeCol);
        }


    }

     public static void printTab(ResultSet fieldData, View view){

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
    public static boolean verifyParams(int[] countParam, int countInputParams, View view){

        Arrays.sort(countParam);
        if (Arrays.binarySearch(countParam,countInputParams) < 0) {

            view.warningWriteln(String.format("Invalid number of parameters. Expected from %s to %s, but got %s",
                    countParam[0], countParam[countParam.length - 1], countInputParams));

            return false;
        }else {
            return true;
        }

    }

    public static boolean requestForConfirmation(View view, String objForDel){
        view.warningWriteln("Are you sure to delete table "+objForDel+" (y/n)");
        return view.read().equals("y");
    }

}
