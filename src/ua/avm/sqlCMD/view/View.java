package ua.avm.sqlCMD.view;

import java.util.ArrayList;

/**
 * Created by AVM on 12.10.2016.
 */
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

    void printTitle(String[] columnList, int sizeCol);
    void printTableData(ArrayList<String[]> tableData, int sizeCol);
    void printFooter(int sizeCol);
}
