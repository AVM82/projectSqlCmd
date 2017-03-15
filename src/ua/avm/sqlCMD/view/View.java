package ua.avm.sqlCMD.view;

import java.util.ArrayList;
import java.util.Map;


public interface View {

    String COMMAND_DELIMITER = "\u0020"+"-";
    String SECONDARY_DELIMITER = "\\|";

    void writeln(String message);
    void warningWriteln(String message);
    void write(String message);
    void fWriteln(String message, int length);
    void setPrefix(String prefix);
    String getCommandDelimiter();
    String getSecondaryDelimiter();


    String read();
    boolean requestForConfirmation();

    void printTitle(String[] columnList, int sizeCol);
    void printTableData(ArrayList<String[]> tableData, int sizeCol);
    void printTableData(Map<String, String> data, String[] title, int sizeCol);
    void printFooter(int sizeCol);
}
