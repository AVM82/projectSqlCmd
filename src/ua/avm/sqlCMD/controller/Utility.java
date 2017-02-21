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
        view.warningWriteln("Are you sure? (y/n)");
        return view.read().equals("y");
    }


}
